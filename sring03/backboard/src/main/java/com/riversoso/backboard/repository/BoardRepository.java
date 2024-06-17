package com.riversoso.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riversoso.backboard.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    
}
