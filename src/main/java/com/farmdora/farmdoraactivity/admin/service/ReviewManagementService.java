package com.farmdora.farmdoraactivity.admin.service;

import com.farmdora.farmdoraactivity.admin.dto.ReviewDTO.*;
import com.farmdora.farmdoraactivity.admin.dto.SearchType;
import com.farmdora.farmdoraactivity.admin.dto.SortType;
import com.farmdora.farmdoraactivity.admin.repository.OrderOptionRepository;
import com.farmdora.farmdoraactivity.admin.repository.ReviewFileRepository;
import com.farmdora.farmdoraactivity.admin.repository.ReviewManagementRepository;
import com.farmdora.farmdoraactivity.common.exception.ResourceAlreadyExistsException;
import com.farmdora.farmdoraactivity.common.exception.ResourceNotFoundException;
import com.farmdora.farmdoraactivity.common.response.PageResponseDto;
import com.farmdora.farmdoraactivity.entity.OrderOption;
import com.farmdora.farmdoraactivity.entity.Review;
import com.farmdora.farmdoraactivity.entity.ReviewFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewManagementService {

    private final ReviewManagementRepository reviewManagementRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final OrderOptionRepository orderOptionRepository;
    private final NCPObjectStorageService ncpImageService;

    @Transactional(readOnly = true)
    public PageResponseDto<ReviewListResponse> getAllReviews(SortType sortType, int page, SearchType searchType, String keyword) {
        Pageable pageable = null;

        if (sortType.equals(SortType.LATEST)) {
            pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        } else {
            pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "createdDate"));
        }

        Page<Review> reviewPage;

        // 검색 조건이 있는 경우
        if(searchType != null && keyword != null && !keyword.trim().isEmpty()) {
            switch (searchType) {
                case PRODUCT_NAME ->
                    reviewPage = reviewManagementRepository.findByProductNameContaining(keyword, pageable);
                case WRITER ->
                    reviewPage = reviewManagementRepository.findByUserNameContaining(keyword, pageable);
                default ->
                    reviewPage = reviewManagementRepository.findAll(pageable);
            }
        } else {
            reviewPage = reviewManagementRepository.findAll(pageable);
        }

        List<ReviewListResponse> reviewListResponses = reviewPage.getContent().stream()
                        .map(ReviewListResponse::fromEntity).toList();

        return new PageResponseDto<>(reviewListResponses, reviewPage);
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewDetail(Integer reviewId) {
        Review review = reviewManagementRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("review", reviewId));

        List<ReviewFile> reviewFiles = reviewFileRepository.findByReviewId(reviewId);

        List<OrderOption> orderOptions = orderOptionRepository.findByOrderId(review.getOrder().getId());

        return ReviewDetailResponse.fromEntity(review, reviewFiles, orderOptions, ncpImageService);
    }

    @Transactional
    public DeleteReviewResponse deleteReview(Integer reviewId) {
        try {
            List<ReviewFile> reviewFiles = reviewFileRepository.findByReviewId(reviewId);

            for(ReviewFile file : reviewFiles) {
                ncpImageService.delete(file.getSaveFile());
            }

            reviewFileRepository.deleteByReviewId(reviewId);

            Review review = reviewManagementRepository.findById(reviewId)
                    .orElseThrow(() -> new ResourceAlreadyExistsException("review", reviewId));

            reviewManagementRepository.delete(review);

            return DeleteReviewResponse.success(reviewId);
        } catch (Exception e) {
            return DeleteReviewResponse.fail(reviewId);
        }
    }
}
