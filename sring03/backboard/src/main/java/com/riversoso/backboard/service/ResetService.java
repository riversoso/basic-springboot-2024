package com.riversoso.backboard.service;

import org.springframework.stereotype.Service;

import com.riversoso.backboard.common.NotFoundException;
import com.riversoso.backboard.entity.Reset;
import com.riversoso.backboard.repository.ResetRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Log4j2
public class ResetService {
    
    private final ResetRepository resetRepository;

    public void setReset(String uuid,String email) {
        Reset reset = Reset.builder().uuid(uuid).email(email).regDate(LocalDateTime.now()).build();

        this.resetRepository.save(reset);
        log.info("■ ■ ■ ■ setReset() 성공!!");
    }

    public Reset getReset(String uuid) {
        Optional<Reset> _reset = this.resetRepository.findByUuid(uuid);

        if (_reset.isPresent()) {
            log.info("■ ■ ■ ■ getReset() 데이터 있음!!");
            return _reset.get();
        }else {
            throw new NotFoundException("Reset not found!");
        }
    }
}
