package com.zerobase.communityproject.member.repository;

import com.zerobase.communityproject.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findByUserId(String userId);

	Optional<Member> findByUserIdAndUserName(String userId, String userName);

	Optional<Member> findByResetPasswordKey(String resetPasswordKey);
}
