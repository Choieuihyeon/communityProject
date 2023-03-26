package com.zerobase.communityproject.member.service;

import com.zerobase.communityproject.exception.CustomException;
import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.model.MemberInput;
import com.zerobase.communityproject.member.model.MemberParam;
import com.zerobase.communityproject.member.model.ResetPasswordInput;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);

	List<MemberDto> list(MemberParam parameter);

	MemberDto detail(String userId);

	CustomException updateMember(MemberInput parameter);

	List<MemberDto> listAll();

	MemberDto frontDetail(String userId);

	boolean sendResetPassword(ResetPasswordInput parameter);

	CustomException updateMemberPassword(MemberInput parameter);

	CustomException withdraw(String userId, String password);

	boolean checkResetPassword(String uuid);

	boolean resetPassword(String id, String password);

}
