package com.riversoso.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riversoso.backboard.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    
}
