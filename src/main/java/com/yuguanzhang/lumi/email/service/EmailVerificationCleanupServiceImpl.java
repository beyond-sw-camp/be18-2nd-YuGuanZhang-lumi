package com.yuguanzhang.lumi.email.service;

import com.yuguanzhang.lumi.email.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationCleanupServiceImpl implements EmailVerificationCleanupService {

    private final EmailVerificationRepository emailVerificationRepository;

    // 스케줄러 폴더로 따로 뺴서 인터페이스 대신 000Scheduler라는 클래스명으로 사용 나중에 고려
    @Override
    @Scheduled(fixedDelay = 600000)
    @Transactional
    public void cleanupExpiredVerifications() {
        log.info("만료된 이메일 인증 상태 변경 작업을 시작합니다.");
        LocalDateTime now = LocalDateTime.now();
        emailVerificationRepository.updateVerifications(now);
        log.info("만료된 이메일 인증 상태 변경 완료. 시간: {}", now);
    }
}
