package com.zerobase.communityproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	OK(HttpStatus.OK, "OK"),
	MEMBER_NOT_EXIT(HttpStatus.BAD_REQUEST, "회원 정보가 존재하지 않습니다."),
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");


	private final HttpStatus status;
	private final String message;

}
