package com.zerobase.communityproject.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponse {

	private final HttpStatus status;
	private final String code;
	private final String message;


}
