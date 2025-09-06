package com.yuguanzhang.lumi.email.repository;

import com.yuguanzhang.lumi.email.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {

    Optional<EmailVerification> findByEmail(String email);

    boolean existsByEmailAndVerifiedIsTrue(String email);

}
