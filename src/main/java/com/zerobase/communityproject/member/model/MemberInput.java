package com.zerobase.communityproject.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MemberInput {

	private String userId;
	private String userName;
	private String password;
	private String gender;
	private String newPassword;

}
