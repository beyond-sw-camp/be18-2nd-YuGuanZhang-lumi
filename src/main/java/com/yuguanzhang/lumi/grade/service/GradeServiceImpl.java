package com.yuguanzhang.lumi.grade.service;

import com.yuguanzhang.lumi.channel.entity.ChannelUser;
import com.yuguanzhang.lumi.channel.repository.ChannelUserRepository;
import com.yuguanzhang.lumi.common.exception.GlobalException;
import com.yuguanzhang.lumi.common.exception.message.ExceptionMessage;
import com.yuguanzhang.lumi.common.service.RoleAuthorizationService;
import com.yuguanzhang.lumi.grade.Entity.Grade;
import com.yuguanzhang.lumi.grade.dto.GradeCategoryGroupDto;
import com.yuguanzhang.lumi.grade.dto.GradeRequestDto;
import com.yuguanzhang.lumi.grade.dto.GradeResponseDto;
import com.yuguanzhang.lumi.grade.repository.GradeRepository;
import com.yuguanzhang.lumi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    private final ChannelUserRepository channelUserRepository;

    private final RoleAuthorizationService roleAuthorizationService;

    @Override
    @Transactional
    public GradeResponseDto createGrade(Long channelUserId, GradeRequestDto gradeRequestDto,
                                        User user) {

        //채널에 참가한 유저 객체 가져오기
        ChannelUser channelUser = channelUserRepository.findById(channelUserId)
                                                       .orElseThrow(() -> new GlobalException(
                                                               ExceptionMessage.CHANNEL_USER_NOT_FOUND));
        //그 유저가 그 채널의 튜터 검증
        roleAuthorizationService.checkTutor(channelUser.getChannel()
                                                       .getChannelId(), channelUser.getUser()
                                                                                   .getUserId());

        // 성적 생성
        Grade grade = Grade.builder()
                           .title(gradeRequestDto.getTitle())
                           .category(gradeRequestDto.getCategory())
                           .grades(gradeRequestDto.getGrades())
                           .date(gradeRequestDto.getDate())
                           .channelUser(channelUser)
                           .build();

        // 성적 db에 저장
        Grade saved = gradeRepository.save(grade);

        return GradeResponseDto.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeCategoryGroupDto> getGradesGroupedByCategory(Long channelUserId) {

        //채널에 참가한 유저 객체 생성
        ChannelUser channelUser = channelUserRepository.findById(channelUserId)
                                                       .orElseThrow(() -> new GlobalException(
                                                               ExceptionMessage.CHANNEL_USER_NOT_FOUND));

        // 그 유저가 생성한 모든 성적을 리스트로 가져옴
        List<GradeResponseDto> grades = gradeRepository.findByChannelUser(channelUser)
                                                       .stream()
                                                       .map(GradeResponseDto::fromEntity)
                                                       .toList();

        // grades를 스트림에 넣어서 groupingBy로 map 형식의 key, value로 변환
        // 변환된 map 형식의 데이터를 다시 entrySet 으로 가공, 그걸 다시 스트림에 넣어서
        // entrySet으로 가공된 데이터를 다시  GradeCategoryGroupDto 객체로 변환,
        // key 값을 첫번째 매개변수, value 값을 두번째 매개변수로
        // 그걸 toList() 리스트로 변환
        // 최종 리턴 타입 : GradeCategoryGroupDto 객체의 리스트
        // ㅈㄴ복잡함
        return grades.stream()
                     .collect(Collectors.groupingBy(GradeResponseDto::getCategory))
                     .entrySet()
                     .stream()
                     .map(entry -> new GradeCategoryGroupDto(entry.getKey(), entry.getValue()))
                     .toList();
    }

    @Override
    @Transactional
    public GradeResponseDto updateGrade(Long gradeId, GradeRequestDto gradeRequestDto, User user) {

        //수정할 성적 객체 생성
        Grade grade = gradeRepository.findById(gradeId)
                                     .orElseThrow(() -> new GlobalException(
                                             ExceptionMessage.GRADE_NOT_FOUND));

        //수정을 요청한 유저가 수정할 권한이 있는지 검증
        roleAuthorizationService.checkTutor(grade.getChannelUser()
                                                 .getChannel()
                                                 .getChannelId(), grade.getChannelUser()
                                                                       .getUser()
                                                                       .getUserId());

        grade.updateGrade(gradeRequestDto.getTitle(), gradeRequestDto.getCategory(),
                          gradeRequestDto.getGrades(), gradeRequestDto.getDate());

        return GradeResponseDto.fromEntity(grade);
    }

    @Override
    @Transactional
    public void deleteGrade(Long gradeId, User user) {

        //삭제할 성적 객체 생성
        Grade grade = gradeRepository.findById(gradeId)
                                     .orElseThrow(() -> new GlobalException(
                                             ExceptionMessage.GRADE_NOT_FOUND));

        //삭제를 요청한 유저가 수정할 권한이 있는지 검증
        roleAuthorizationService.checkTutor(grade.getChannelUser()
                                                 .getChannel()
                                                 .getChannelId(), grade.getChannelUser()
                                                                       .getUser()
                                                                       .getUserId());

        //성적 삭제
        gradeRepository.delete(grade);

    }
}
