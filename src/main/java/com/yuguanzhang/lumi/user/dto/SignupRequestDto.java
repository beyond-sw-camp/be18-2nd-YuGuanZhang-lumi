package com.yuguanzhang.lumi.user.dto;

import com.yuguanzhang.lumi.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
public class SignupRequestDto {

    private final String email;
    private final String name;
    private final String password;
    private final String provider;

    @NotNull(message = "개인정보 동의가 필요합니다.")
    private final Boolean isPrivacyAgreement;

    public SignupRequestDto(String email, String name, String password, String provider,
            Boolean isPrivacyAgreement) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.provider = provider;
        this.isPrivacyAgreement = isPrivacyAgreement;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder().email(email).name(name).password(passwordEncoder.encode(password))
                .provider(provider == null ? "local" : provider)
                .isPrivacyAgreement(isPrivacyAgreement).build();
    }
}
