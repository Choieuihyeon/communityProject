package com.zerobase.communityproject.member.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "member")
public class Member {

	@Id
	private String userId;    // 유저 아이디(이메일, 중복불가)

	private String userName;    // 유저 이름
	private String password;    // 유저 비밀번호

	private String gender;    // 유저 성별("남" = 남성, "여" = 여성)

	private LocalDateTime registeredAt;    // 유저 등록일
	private boolean adminYn;    // 관리자 여부

	// 새로 추가
	private String resetPasswordKey;
	private LocalDateTime resetPasswordLimitDt;
}
