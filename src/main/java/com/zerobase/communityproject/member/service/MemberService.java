package com.zerobase.communityproject.member.service;

import com.zerobase.communityproject.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

	boolean register(MemberInput parameter);


}
