package com.yuguanzhang.lumi.user.service.serach;

import com.yuguanzhang.lumi.user.dto.search.SearchRequestDto;
import com.yuguanzhang.lumi.user.dto.search.SearchResoponseDto;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final UserRepository userRepository;

    @Override
    public SearchResoponseDto searchUser(final UUID userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return SearchResoponseDto.searchResoponseDto(user);
    }
}
