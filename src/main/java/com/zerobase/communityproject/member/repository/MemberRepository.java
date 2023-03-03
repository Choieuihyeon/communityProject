package com.zerobase.communityproject.member.repository;

import com.zerobase.communityproject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {


}
