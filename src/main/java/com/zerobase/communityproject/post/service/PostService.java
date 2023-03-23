package com.zerobase.communityproject.post.service;

import com.zerobase.communityproject.post.dto.PostDto;
import com.zerobase.communityproject.post.model.PostInput;
import com.zerobase.communityproject.post.model.PostParam;
import java.security.Principal;
import java.util.List;

public interface PostService {

	boolean add(PostInput parameter, Principal principal);

	boolean set(PostInput parameter);

	List<PostDto> list(PostParam parameter);

	boolean delete(String idList);

	PostDto frontDetail(long id);

	List<PostDto> list();

	PostDto getById(long id);

	PostDto detail(String title);

	List<PostDto> myPost(String userId);

}
