package com.yuguanzhang.lumi.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { // 회원가입 페이지에 로그인 없이 접근하기 위한 코드

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Postman 테스트 시 CSRF 비활성화
                .authorizeHttpRequests(
                        (authorize) -> authorize.requestMatchers("/api/login", "/sign-up",
                                        "/api/sign-up", "/connect/**").permitAll().anyRequest()
                                .authenticated()
                        // authenticated은 인증된 사용자에 대한 요청만 허가한다.
                ).httpBasic(Customizer.withDefaults()).formLogin(
                        form -> form.loginPage("/api/login").loginProcessingUrl("/api/login")
                                .defaultSuccessUrl("/main", true).failureUrl("/api/login?error=true"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Argon2 대신 BCryptPasswordEncoder 사용
    }
}
