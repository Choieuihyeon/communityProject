package com.zerobase.communityproject.post.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity (name = "post")
public class Post {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;    // 순번

	private String userId;    // 유저 아이디(유저 이메일)
	private String title;    // 게시물 제목
	private String contents;    // 게시물 상세 내용
	private LocalDateTime registeredAt;    // 게시물 등록일
	private LocalDateTime modifiedAt;    // 게시물 수정일

}
