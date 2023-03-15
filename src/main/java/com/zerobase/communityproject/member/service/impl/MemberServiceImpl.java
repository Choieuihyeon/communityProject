package com.zerobase.communityproject.member.service.impl;

import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.entity.Member;
import com.zerobase.communityproject.member.mapper.MemberMapper;
import com.zerobase.communityproject.member.model.MemberInput;
import com.zerobase.communityproject.member.model.MemberParam;
import com.zerobase.communityproject.member.repository.MemberRepository;
import com.zerobase.communityproject.member.service.MemberService;
import com.zerobase.communityproject.post.model.ServiceResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

	// 회원가입
	@Override
	public boolean register(MemberInput parameter) {

		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		if (optionalMember.isPresent()) {
			// 현재 userId에 해당하는 데이터 존재
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
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if (!optionalMember.isPresent()) {
			return null;
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

	/*
	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}

	@Override
	public List<MemberDto> list() {
		List<Member> members = memberRepository.findAll((getSortBySortValueDesc()));
		return MemberDto.of(members);

	}

	 */


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

}

