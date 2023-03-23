package com.zerobase.communityproject.member.service.impl;

import com.zerobase.communityproject.common.model.components.MailComponents;
import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.entity.Member;
import com.zerobase.communityproject.member.mapper.MemberMapper;
import com.zerobase.communityproject.member.model.MemberInput;
import com.zerobase.communityproject.member.model.MemberParam;
import com.zerobase.communityproject.member.model.ResetPasswordInput;
import com.zerobase.communityproject.member.repository.MemberRepository;
import com.zerobase.communityproject.member.service.MemberService;
import com.zerobase.communityproject.post.model.ServiceResult;
import com.zerobase.communityproject.util.PasswordUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	private final MailComponents mailComponents;

	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}

	@Override
	public boolean register(MemberInput parameter) {

		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		if (optionalMember.isPresent()) {
			return false;
		}

		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

		Member member = Member.builder()
			.userId(parameter.getUserId())
			.userName(parameter.getUserName())
			.password(encPassword)
			.gender(parameter.getGender())
			.adminYn(false)
			.registeredAt(LocalDateTime.now())
			.build();
		memberRepository.save(member);

		return true;
	}

	@Override
	public List<MemberDto> list(MemberParam parameter) {

		long totalCount = memberMapper.selectListCount(parameter);

		List<MemberDto> list = memberMapper.selectList(parameter);
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (MemberDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount - parameter.getPageStart() - i);
				i++;
			}
		}
		return list;
	}

	@Override
	public MemberDto detail(String userId) {
		Optional<Member> optionalMember = memberRepository.findByUserId(userId);
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		Member member = optionalMember.get();

		return MemberDto.of(member);
	}

	@Override
	public ServiceResult updateMember(MemberInput parameter) {

		String userId = parameter.getUserId();

		Optional<Member> optionalMember = memberRepository.findById(userId);
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		Member member = optionalMember.get();

		member.setRegisteredAt(LocalDateTime.now());
		memberRepository.save(member);

		return new ServiceResult();
	}

	@Override
	public List<MemberDto> listAll() {

		List<Member> memberList = memberRepository.findAll();
		return MemberDto.of(memberList);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findById(username);
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		Member member = optionalMember.get();

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (member.isAdminYn()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
	}

	@Override
	public MemberDto frontDetail(String userId) {

		Optional<Member> optionalMember = memberRepository.findByUserId(userId);
		if (optionalMember.isPresent()) {
			return MemberDto.of(optionalMember.get());
		}
		return null;
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(
			parameter.getUserId(), parameter.getUserName());
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		Member member = optionalMember.get();

		String uuid = UUID.randomUUID().toString();
		member.setResetPasswordKey(uuid);
		member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));

		memberRepository.save(member);

		String email = parameter.getUserId();
		String subject = "[fastlms] 비밀번호 초기화 메일 입니다.";
		String text = "<p>fastlms 비밀번호 초기화 메일 입니다.</p><p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"
			+ "<div><a target='_blank' href='http://localhost:8080/member/reset/password?id=" + uuid
			+ "'> 비밀번호 초기화 링크 </a></div>";
		mailComponents.sendMail(email, subject, text);

		return true;
	}

	@Override
	public ServiceResult updateMemberPassword(MemberInput parameter) {

		String userId = parameter.getUserId();

		Optional<Member> optionalMember = memberRepository.findById(userId);
		if (!optionalMember.isPresent()) {
			return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
		}

		Member member = optionalMember.get();

		if (!PasswordUtils.equals(parameter.getPassword(), member.getPassword())) {
			return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
		}

		String encPassword = PasswordUtils.encPassword(parameter.getNewPassword());
		member.setPassword(encPassword);
		memberRepository.save(member);

		return new ServiceResult(true);
	}

	@Override
	public ServiceResult withdraw(String userId, String password) {

		Optional<Member> optionalMember = memberRepository.findById(userId);
		if (!optionalMember.isPresent()) {
			return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
		}

		Member member = optionalMember.get();

		if (!PasswordUtils.equals(password, member.getPassword())) {
			return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
		}

		member.setUserName("삭제회원");
		member.setPassword("");
		member.setGender("");
		member.setRegisteredAt(null);
		memberRepository.save(member);

		return new ServiceResult();
	}

	@Override
	public boolean checkResetPassword(String uuid) {

		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if (!optionalMember.isPresent()) {
			return false;
		}

		Member member = optionalMember.get();

		// 초기화 날짜가 유효한지 체크
		if (member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException(" 유효한 날짜가 아닙니다 ");
		}

		if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException(" 유효한 날짜가 아닙니다 ");
		}

		return true;
	}

	@Override
	public boolean resetPassword(String uuid, String password) {

		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		Member member = optionalMember.get();

		// 초기화 날짜가 유효한지 체크
		if (member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException(" 유효한 날짜가 아닙니다 ");
		}

		if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException(" 유효한 날짜가 아닙니다 ");
		}

		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		member.setPassword(encPassword);
		member.setResetPasswordKey("");
		member.setResetPasswordLimitDt(null);
		memberRepository.save(member);

		return true;
	}
}

