package com.zerobase.communityproject.member.service;

import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.model.MemberInput;
import com.zerobase.communityproject.member.model.MemberParam;
import com.zerobase.communityproject.member.model.ResetPasswordInput;
import com.zerobase.communityproject.post.model.ServiceResult;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);

	List<MemberDto> list(MemberParam parameter);

	MemberDto detail(String userId);

	ServiceResult updateMember(MemberInput parameter);

	List<MemberDto> listAll();

	MemberDto frontDetail(String userId);

	boolean sendResetPassword(ResetPasswordInput parameter);

	ServiceResult updateMemberPassword(MemberInput parameter);

	ServiceResult withdraw(String userId, String password);

	boolean checkResetPassword(String uuid);

	boolean resetPassword(String id, String password);

}
