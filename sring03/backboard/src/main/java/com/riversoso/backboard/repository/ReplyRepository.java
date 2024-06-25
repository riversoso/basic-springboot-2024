package com.riversoso.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riversoso.backboard.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
}
