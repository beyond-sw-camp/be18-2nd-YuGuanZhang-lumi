package com.yuguanzhang.lumi.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String provider;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    // dto에 따로 뺄거임

    // 사용자가 가진 권한(ROLE)을 반환.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // 로그인할 때 "사용자 이름"으로 뭘 쓸지를 지정.
    @Override
    public String getUsername() {
        return email;
    }


    //    계정이 만료되었는지? (false면 로그인 불가)
    //
    //    계정이 잠겼는지? (false면 로그인 불가)
    //
    //    비밀번호가 만료되었는지? (false면 로그인 불가)
    //
    //    계정이 활성화(enabled) 상태인지? (false면 로그인 불가)


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
