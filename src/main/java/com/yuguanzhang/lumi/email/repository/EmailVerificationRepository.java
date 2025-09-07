package com.yuguanzhang.lumi.email.repository;

import com.yuguanzhang.lumi.email.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {

    Optional<EmailVerification> findByEmail(String email);

    boolean existsByEmailAndVerifiedIsTrue(String email);

    // ✅ 만료 시간이 지난 레코드를 삭제하는 메서드 추가
    @Modifying // @Modifying은 DELETE, UPDATE 쿼리에 필요합니다.
    @Transactional // 삭제는 트랜잭션 내에서 실행되어야 합니다.
    @Query("DELETE FROM EmailVerification ev WHERE ev.expiration_at < :expirationTime")
    void deleteByExpirationAtBefore(LocalDateTime expirationTime);
}
