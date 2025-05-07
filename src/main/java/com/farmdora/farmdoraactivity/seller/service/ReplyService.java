package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.entity.Review;
import com.farmdora.farmdoraactivity.seller.dto.ReviewDTO;
import com.farmdora.farmdoraactivity.seller.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyReviewRepository replyReviewRepository;
    private final ReplyQuestionRepository replyQuestionRepository;

    public void updateReviewReply(ReviewDTO reviewDTO) {
        setReviewReply(reviewDTO);
        log.info("리뷰 답변 수정 완료: {}", reviewDTO.getReviewId());
    }

    public void insertReviewReply(ReviewDTO reviewDTO) {
        setReviewReply(reviewDTO);
        log.info("리뷰 답변 등록 완료: {}", reviewDTO.getReviewId());
    }

    public void deleteReviewReply(Integer reviewId) {
        Review review = replyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다. ID: " + reviewId));
        review.setReply(null);
    }

    // 공통 로직을 private 메서드로 추출
    private void setReviewReply(ReviewDTO reviewDTO) {
        Review review = replyReviewRepository.findById(reviewDTO.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다. ID: " + reviewDTO.getReviewId()));

        review.setReply(reviewDTO.getReply());
    }


}
