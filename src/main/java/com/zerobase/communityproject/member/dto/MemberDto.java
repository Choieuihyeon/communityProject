package com.zerobase.communityproject.member.dto;

import com.zerobase.communityproject.member.entity.Member;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {

	String userId;

	String userName;
	String password;
	String gender;
	LocalDateTime registeredAt;

	boolean adminYn;

	// 추가컬럼
	//long totalCount;	게시물 목록 조회수 카운트
	//long seq;

	public static MemberDto of(Member member) {

		return MemberDto.builder()
			.userId(member.getUserId())
			.userName(member.getUserName())
			.password(member.getPassword())
			.gender(member.getGender())
			.registeredAt(member.getRegisteredAt())
			.adminYn(member.isAdminYn())
			.build();
	}

	public String getRegisteredAtText() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return registeredAt != null ? registeredAt.format(formatter) : "";
	}


}
