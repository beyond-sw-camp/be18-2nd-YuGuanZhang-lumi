package com.yuguanzhang.lumi.email.service;

public interface EmailVerificationService {

    void sendVerificationEmail(String email);

    boolean verifyEmail(String token);

    boolean isEmailVerified(String email);
}
