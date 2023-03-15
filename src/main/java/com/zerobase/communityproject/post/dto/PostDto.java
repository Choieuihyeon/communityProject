package com.zerobase.communityproject.post.dto;

import com.zerobase.communityproject.post.entity.Post;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostDto {

	long id;
	String userId;
	String title;
	String contents;
	LocalDateTime registeredAt;
	LocalDateTime modifiedAt;

	// 추가 컬럼
	long totalCount;    // 게시물 목록 조회수 카운트
	long seq;

	int postCount;

	public static PostDto of(Post post) {

		return PostDto.builder()
			.id(post.getId())
			.userId(post.getUserId())
			.title(post.getTitle())
			.contents(post.getContents())
			.registeredAt(post.getRegisteredAt())
			.modifiedAt(post.getModifiedAt())
			.build();
	}

	public String getRegisteredAtText() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return registeredAt != null ? registeredAt.format(formatter) : "";
	}

	public String getModifiedAtText() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return modifiedAt != null ? modifiedAt.format(formatter) : "";
	}

	public static List<PostDto> of(List<Post> posts) {
		if (posts != null) {
			List<PostDto> postList = new ArrayList<>();
			for (Post x : posts) {
				postList.add(of(x));
			}
			return postList;
		}
		return Collections.emptyList();
	}


}

