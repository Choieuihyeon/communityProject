package com.zerobase.communityproject.post.service;

import com.zerobase.communityproject.post.dto.PostDto;
import com.zerobase.communityproject.post.model.PostInput;
import com.zerobase.communityproject.post.model.PostParam;
import java.util.List;

public interface PostService {

	/**
	 * 게시물 등록
	 */
	boolean add(PostInput parameter);

	/**
	 * 게시물 수정
	 */
	boolean set(PostInput parameter);

	/**
	 * 게시물 목록
	 */
	List<PostDto> list(PostParam parameter);

	/**
	 * 전체 게시물 목록
	 */
	List<PostDto> listAll();

	/**
	 * 게시물 내용 삭제
	 */
	boolean delete(String idList);

	/**
	 * 프론트 게시물 목록
	 */
	//List<PostDto> frontList(PostParam parameter);

	/**
	 * 게시물 상세 정보
	 */
	PostDto frontDetail(long id);

	List<PostDto> list();

	PostDto getById(long id);

	/**
	 * 프론트 게시물 목록
	 */
	List<PostDto> frontList(PostParam parameter);

	/**
	 * 게시물 상세 정보
	 */
	PostDto detail(String title);

}
