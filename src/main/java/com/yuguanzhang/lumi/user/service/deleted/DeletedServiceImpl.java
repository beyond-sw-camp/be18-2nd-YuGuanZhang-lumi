package com.yuguanzhang.lumi.user.service.deleted;

import com.yuguanzhang.lumi.user.dto.deleted.DeletedRequestDto;
import com.yuguanzhang.lumi.user.dto.deleted.DeletedResponseDto;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletedServiceImpl implements DeletedService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public DeletedResponseDto deleted(DeletedRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
                                  .orElseThrow(
                                          () -> new IllegalArgumentException("해당 이메일의 사용자가 없습니다."));

        user.markAsDeleted();

        return new DeletedResponseDto(user.getEmail());
    }
}
