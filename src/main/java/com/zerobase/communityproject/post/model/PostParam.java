package com.zerobase.communityproject.post.model;

import lombok.Data;

@Data
public class PostParam extends CommonParam {

	long Id;
	String userId;
	String title;
	String contents;
}
