package com.farmdora.farmdoraactivity.admin.service;

import com.farmdora.farmdoraactivity.admin.dto.ReviewDTO.*;
import com.farmdora.farmdoraactivity.admin.dto.SearchDTO;
import com.farmdora.farmdoraactivity.admin.repository.OrderOptionRepository;
import com.farmdora.farmdoraactivity.admin.repository.ReviewFileRepository;
import com.farmdora.farmdoraactivity.admin.repository.ReviewManagementRepository;
import com.farmdora.farmdoraactivity.common.exception.ResourceNotFoundException;
import com.farmdora.farmdoraactivity.common.response.PageResponseDto;
import com.farmdora.farmdoraactivity.entity.OrderOption;
import com.farmdora.farmdoraactivity.entity.Review;
import com.farmdora.farmdoraactivity.entity.ReviewFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewManagementService {

    private final ReviewManagementRepository reviewManagementRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final OrderOptionRepository orderOptionRepository;
    private final NcpImageService ncpImageService;

    @Transactional(readOnly = true)
    public PageResponseDto<ReviewListResponse> getAllReviews(SearchDTO searchDTO, Pageable pageable) {
        Page<Review> reviewPage = reviewManagementRepository.findAllByCreatedDateBetweenOrderByCreatedDateDesc(
                searchDTO.getStartDate(),
                searchDTO.getEndDate(),
                pageable);

        List<ReviewListResponse> reviewListResponses = reviewPage.getContent().stream()
                .collect(Collectors.groupingBy(
                        review -> review.getOrder().getId(),
                        Collectors.groupingBy(review -> review.getSale().getId())
                ))
                .values().stream()
                .flatMap(orderGroup -> orderGroup.values().stream())
                .map(saleReviews -> {
                    Review review = saleReviews.get(0);
                    List<ReviewFile> reviewFiles = reviewFileRepository.findByReviewId(review.getId());
                    List<OrderOption> orderOptions = orderOptionRepository.findByOrderId(review.getOrder().getId());

                    List<OrderOptionInfo> filteredOrderOptions = orderOptions.stream()
                            .filter(option -> option.getOption().getSale().getId().equals(review.getSale().getId()))
                            .map(OrderOptionInfo::fromEntity)
                            .toList();

                    return ReviewListResponse.fromEntity(review, reviewFiles, filteredOrderOptions, ncpImageService);
                })
                .collect(Collectors.toList());

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
            Review review = reviewManagementRepository.findById(reviewId)
                    .orElseThrow(() -> new ResourceNotFoundException("review", reviewId));

            List<ReviewFile> reviewFiles = reviewFileRepository.findByReviewId(reviewId);

            for(ReviewFile file : reviewFiles) {
                ncpImageService.deleteObjectToNCP(file.getSaveFile());
            }

            reviewManagementRepository.delete(review);

            return DeleteReviewResponse.success(reviewId);
        } catch (Exception e) {
            return DeleteReviewResponse.fail(reviewId);
        }
    }
}
