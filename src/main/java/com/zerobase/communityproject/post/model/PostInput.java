package com.zerobase.communityproject.post.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostInput {

	private long id;
	private String userId;
	private String title;
	private String contents;

	// 삭제를 위한 속성
	private String idList;


}
