package com.zerobase.communityproject.post.model;

import lombok.Data;

@Data
public class ServiceResult {

	boolean result; // true, false
	String message; // false 일시 에러메시지


	public ServiceResult() {
		result = true;
	}

	public ServiceResult(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public ServiceResult(boolean result) {
		this.result = result;
	}
}
