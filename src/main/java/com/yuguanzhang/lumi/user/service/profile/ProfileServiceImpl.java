package com.yuguanzhang.lumi.user.service.profile;

import com.yuguanzhang.lumi.user.dto.profile.ProfileResoponseDto;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public ProfileResoponseDto getProfile(final UUID userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return ProfileResoponseDto.searchResoponseDto(user);
    }
}
