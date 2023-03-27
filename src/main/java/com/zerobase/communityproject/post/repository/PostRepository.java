package com.zerobase.communityproject.post.repository;

import com.zerobase.communityproject.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findByTitle(String title);
}
