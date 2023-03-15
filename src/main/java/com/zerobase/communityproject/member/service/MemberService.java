package com.zerobase.communityproject.member.service;

import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.model.MemberInput;
import com.zerobase.communityproject.member.model.MemberParam;
import com.zerobase.communityproject.post.model.ServiceResult;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);

	/**
	 * 회원 목록 리턴(관리자에서만 사용 가능)
	 */
	List<MemberDto> list(MemberParam parameter);

	/**
	 * 회원 상세 정보
	 */
	MemberDto detail(String userId);

	/**
	 * 회원정보 수정
	 */
	ServiceResult updateMember(MemberInput parameter);


}
