package com.yuguanzhang.lumi.assignment.service;

import com.yuguanzhang.lumi.assignment.dto.EvaluationRequestDto;
import com.yuguanzhang.lumi.assignment.dto.EvaluationResponseDto;
import com.yuguanzhang.lumi.assignment.entity.Evaluation;
import com.yuguanzhang.lumi.assignment.entity.Submission;
import com.yuguanzhang.lumi.assignment.repository.EvaluationRepository;
import com.yuguanzhang.lumi.assignment.repository.SubmissionRepository;
import com.yuguanzhang.lumi.common.exception.GlobalException;
import com.yuguanzhang.lumi.common.exception.message.ExceptionMessage;
import com.yuguanzhang.lumi.common.service.RoleAuthorizationService;
import com.yuguanzhang.lumi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;

    private final SubmissionRepository submissionRepository;

    private final RoleAuthorizationService roleAuthorizationService;

    @Override
    public EvaluationResponseDto createEvaluation(Long channelId, Long submissionId, User user,
                                                  EvaluationRequestDto dto) {
        // 튜터 권한 검증
        roleAuthorizationService.checkTutor(channelId, user.getUserId());

        Submission submission = submissionRepository.findById(submissionId)
                                                    .orElseThrow(() -> new GlobalException(
                                                            ExceptionMessage.SUBMISSION_NOT_FOUND));
        //이미 평가가 있으면 에러
        if (submission.getEvaluation() != null) {
            throw new GlobalException(ExceptionMessage.EVALUATION_ALREADY_EXISTS);
        }

        Evaluation evaluation = Evaluation.builder()
                                          .grade(dto.getGrade())
                                          .feedback(dto.getFeedback())
                                          .submission(submission)
                                          .build();

        Evaluation saved = evaluationRepository.save(evaluation);

        return EvaluationResponseDto.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluationResponseDto getEvaluation(Long channelId, Long submissionId, User user) {
        Submission submission = submissionRepository.findById(submissionId)
                                                    .orElseThrow(() -> new GlobalException(
                                                            ExceptionMessage.SUBMISSION_NOT_FOUND));
        Evaluation evaluation = submission.getEvaluation();
        if (evaluation == null) {
            throw new GlobalException(ExceptionMessage.EVALUATION_NOT_FOUND);
        }

        return EvaluationResponseDto.fromEntity(evaluation);
    }

    @Override
    public EvaluationResponseDto updateEvaluation(Long channelId, Long submissionId, User user,
                                                  EvaluationRequestDto dto) {

        roleAuthorizationService.checkTutor(channelId, user.getUserId());

        Submission submission = submissionRepository.findById(submissionId)
                                                    .orElseThrow(() -> new GlobalException(
                                                            ExceptionMessage.SUBMISSION_NOT_FOUND));

        Evaluation evaluation = submission.getEvaluation();
        if (evaluation == null) {
            throw new GlobalException(ExceptionMessage.EVALUATION_NOT_FOUND);
        }

        evaluation.updateGrade(dto.getGrade());
        evaluation.updateFeedback(dto.getFeedback());

        return EvaluationResponseDto.fromEntity(evaluation);
    }

    @Override
    public void deleteEvaluation(Long channelId, Long submissionId, User user) {

        roleAuthorizationService.checkTutor(channelId, user.getUserId());

        Submission submission = submissionRepository.findById(submissionId)
                                                    .orElseThrow(() -> new GlobalException(
                                                            ExceptionMessage.SUBMISSION_NOT_FOUND));

        Evaluation evaluation = submission.getEvaluation();
        if (evaluation == null) {
            throw new GlobalException(ExceptionMessage.EVALUATION_NOT_FOUND);
        }

        // 삭제가 제출이랑 1:1 매핑이라 단독으로 삭제가 안돼서 제출에 있는 평가 객체를 null로 바꾸면
        // jpa가 알아서 삭제해준다고 합니다.
        submission.updateEvaluation(null);
        
    }
}
