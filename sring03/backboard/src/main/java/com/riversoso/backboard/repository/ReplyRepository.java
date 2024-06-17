package com.riversoso.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riversoso.backboard.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
      
}
