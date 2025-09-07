package com.yuguanzhang.lumi.email.service;

import com.yuguanzhang.lumi.email.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // ✅ Lombok의 @Slf4j 임포트
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationCleanupServiceImpl implements EmailVerificationCleanupService {

    private final EmailVerificationRepository emailVerificationRepository;


    @Override
    @Scheduled(fixedDelay = 900000) // 60초 = 60000ms
    @Transactional
    public void cleanupExpiredVerifications() {
        log.info("만료된 이메일 인증 기록 삭제 작업을 시작합니다.");
        LocalDateTime now = LocalDateTime.now();
        // 만료 시간이 현재 시간보다 이전인 모든 레코드 삭제
        emailVerificationRepository.deleteByExpirationAtBefore(now);
        log.info("만료된 이메일 인증 기록 삭제 작업 완료. 시간: {}", now);
    }
}
