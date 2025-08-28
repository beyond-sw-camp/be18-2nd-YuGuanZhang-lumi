package com.yuguanzhang.lumi.user.dto;

import com.yuguanzhang.lumi.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter // Setter 빼는게 좋을거 같음 / @setter를 안 쓰고 생성자/빌더로 불변 Dto를 만들어도 됨
public class SignupRequestDto {

    private String email;
    private String name;
    private String password;
    private String provider;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder().email(email).name(name).password(passwordEncoder.encode(password))
                .provider(provider).build();
    }
}
