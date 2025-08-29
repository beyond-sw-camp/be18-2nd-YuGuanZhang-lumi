package com.yuguanzhang.lumi.user.dto;

import com.yuguanzhang.lumi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignupRequestDto {

    private final String email;
    private final String name;
    private final String password;
    private final String provider;

    @Builder
    public SignupRequestDto(String email, String name, String password, String provider) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.provider = provider;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder().email(email).name(name).password(passwordEncoder.encode(password))
                .provider(provider).build();
    }
}
