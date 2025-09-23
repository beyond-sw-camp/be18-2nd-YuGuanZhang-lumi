package com.yuguanzhang.lumi.grade.controller;

import com.yuguanzhang.lumi.common.dto.BaseResponseDto;
import com.yuguanzhang.lumi.grade.Entity.Grade;
import com.yuguanzhang.lumi.grade.dto.GradeCategoryGroupDto;
import com.yuguanzhang.lumi.grade.dto.GradeRequestDto;
import com.yuguanzhang.lumi.grade.dto.GradeResponseDto;
import com.yuguanzhang.lumi.grade.service.GradeService;
import com.yuguanzhang.lumi.user.dto.UserDetailsDto;
import com.yuguanzhang.lumi.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/{channel_user_id}/grades")
    public ResponseEntity<BaseResponseDto<GradeResponseDto>> createGrade(
            @PathVariable("channel_user_id") Long channelUserId,
            @AuthenticationPrincipal UserDetailsDto user,
            @Valid @RequestBody GradeRequestDto gradeRequestDto) {

        GradeResponseDto gradeResponseDto =
                gradeService.createGrade(channelUserId, gradeRequestDto, user.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(BaseResponseDto.of(HttpStatus.CREATED, gradeResponseDto));
    }

    @GetMapping("/{channel_user_id}/grades")
    public ResponseEntity<BaseResponseDto<GradeCategoryGroupDto>> getGrade(
            @PathVariable("channel_user_id") Long channelUserId) {

        List<GradeCategoryGroupDto> gradeCategoryGroupDto =
                gradeService.getGradesGroupedByCategory(channelUserId);

        return ResponseEntity.ok(BaseResponseDto.of(HttpStatus.OK, gradeCategoryGroupDto));
    }

    @PutMapping("/{channel_user_id}/grades/{grade_id}")
    public ResponseEntity<BaseResponseDto<GradeResponseDto>> updateGrade(
            @PathVariable("channel_user_id") Long channelUserId,
            @PathVariable("grade_id") Long gradeId, @AuthenticationPrincipal UserDetailsDto user,
            @Valid @RequestBody GradeRequestDto gradeRequestDto) {

        GradeResponseDto gradeResponseDto =
                gradeService.updateGrade(gradeId, gradeRequestDto, user.getUser());

        return ResponseEntity.ok(BaseResponseDto.of(HttpStatus.OK, gradeResponseDto));
    }

    @DeleteMapping("/{channel_user_id}/grades/{grade_id}")
    public ResponseEntity<BaseResponseDto<Void>> deleteGrade(
            @PathVariable("channel_user_id") Long channelUserId,
            @PathVariable("grade_id") Long gradeId, @AuthenticationPrincipal UserDetailsDto user) {

        gradeService.deleteGrade(gradeId, user.getUser());

        return ResponseEntity.ok(BaseResponseDto.of(HttpStatus.OK, null));
    }


}
