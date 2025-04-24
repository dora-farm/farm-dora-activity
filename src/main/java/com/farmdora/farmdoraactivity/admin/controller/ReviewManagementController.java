package com.farmdora.farmdoraactivity.admin.controller;

import com.farmdora.farmdoraactivity.admin.dto.ReviewDTO.*;
import com.farmdora.farmdoraactivity.admin.dto.SearchType;
import com.farmdora.farmdoraactivity.admin.dto.SortType;
import com.farmdora.farmdoraactivity.admin.service.ReviewManagementService;
import com.farmdora.farmdoraactivity.common.response.ErrorMessage;
import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.common.response.PageResponseDto;
import com.farmdora.farmdoraactivity.common.response.SuccessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewManagementController {

    private final ReviewManagementService reviewManagementService;

    @GetMapping("/allreviews")
    public ResponseEntity<?> getAllReviews(
            @RequestParam SortType sortType,
            @RequestParam int page,
            @RequestParam(value = "searchType", required = false) SearchType searchType,
            @RequestParam(value = "keyword", required = false) String keyword) {

        PageResponseDto<ReviewListResponse> pageResponse = reviewManagementService.getAllReviews(sortType, page, searchType, keyword);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SuccessMessage.SEARCH_REVIEW_ALL_SUCCESS.getMessage(), pageResponse));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewDetail(@PathVariable("reviewId") Integer reviewId) {
        ReviewDetailResponse reviewDetail = reviewManagementService.getReviewDetail(reviewId);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SuccessMessage.SEARCH_REVIEW_DETAIL_SUCCESS.getMessage(), reviewDetail));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Integer reviewId) {
        DeleteReviewResponse deleteReview = reviewManagementService.deleteReview(reviewId);

        if(deleteReview.isDeleted()) {
            return ResponseEntity.ok()
                    .body(new HttpResponse(HttpStatus.OK, SuccessMessage.REVIEW_DELETE_SUCCESS.getMessage(), deleteReview));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.REVIEW_DELETE_FAIL.getMessage(), deleteReview));
        }
    }
}
