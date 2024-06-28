package com.riversoso.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.riversoso.backboard.entity.Reset;

public interface ResetRepository extends JpaRepository<Reset,Integer> {
    
    Optional<Reset> findByUuid(String uuid); //UUID로 테이블 검색
}
