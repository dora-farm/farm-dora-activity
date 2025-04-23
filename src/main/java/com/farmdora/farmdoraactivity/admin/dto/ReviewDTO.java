package com.farmdora.farmdoraactivity.admin.dto;

import com.farmdora.farmdoraactivity.entity.*;
import com.farmdora.farmdoraactivity.admin.service.NcpImageService;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewListResponse {
        private Integer reviewId;
        private Integer saleId;
        private String productName;
        private String productImage;
        private String content;
        private byte score;
        private LocalDateTime createdDate;
        private String userName;

        // 목록 조회용 간략 정보로 변환
        public static ReviewListResponse fromEntity(
                Review review,
                List<ReviewFile> reviewFiles,
                List<OrderOptionInfo> orderOptions,
                NcpImageService ncpImageService) {
            Sale sale = review.getSale();
            User user = review.getOrder().getUser();

            // 파일명을 전체 URL로 변환
            List<String> imageUrls = reviewFiles.stream()
                    .map(file -> ncpImageService.getObjectUrl(file.getSaveFile()))
                    .collect(Collectors.toList());

            // 리뷰의 saleId와 동일한 옵션만 필터링
            List<OrderOptionInfo> filteredOrderOptions = orderOptions.stream()
                    .filter(option -> option.getSaleId().equals(sale.getId()))
                    .collect(Collectors.toList());

            return ReviewListResponse.builder()
                    .reviewId(review.getId())
                    .saleId(sale.getId())
                    .productName(sale.getTitle())
                    .productImage(ncpImageService.getObjectUrl(sale.getSeller().getSaveFile()))
                    .content(review.getContent().length() > 50 ?
                            review.getContent().substring(0, 50) + "..." : review.getContent())
                    .score(review.getScore())
                    .createdDate(review.getCreatedDate())
                    .userName(user.getName())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewDetailResponse {
        private Integer reviewId;
        private Integer saleId;
        private String productName;
        private String productImage;
        private String content;
        private byte score;
        private LocalDateTime createdDate;
        private List<String> imageUrls;
        private List<OrderOptionInfo> orderOptions;
        private String userName;  // 리뷰 작성자 정보

        // 상세 조회용 상세 정보로 변환
        public static ReviewDetailResponse fromEntity(Review review, List<ReviewFile> reviewFiles,
                                                      List<OrderOption> orderOptions, NcpImageService ncpImageService) {
            Sale sale = review.getSale();
            User user = review.getOrder().getUser();
            // 파일명을 전체 URL로 변환
            List<String> imageUrls = reviewFiles.stream()
                    .map(file -> ncpImageService.getObjectUrl(file.getSaveFile()))
                    .collect(Collectors.toList());

            // 리뷰의 saleId와 동일한 옵션만 필터링하여 변환
            List<OrderOptionInfo> options = orderOptions.stream()
                    .filter(option -> option.getOption().getSale().getId().equals(sale.getId()))
                    .map(OrderOptionInfo::fromEntity)
                    .collect(Collectors.toList());

            return ReviewDetailResponse.builder()
                    .reviewId(review.getId())
                    .saleId(sale.getId())
                    .productName(sale.getTitle())
                    .productImage(ncpImageService.getObjectUrl(sale.getSeller().getSaveFile()))
                    .content(review.getContent())
                    .score(review.getScore())
                    .createdDate(review.getCreatedDate())
                    .imageUrls(imageUrls)
                    .orderOptions(options)
                    .userName(user.getName())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderOptionInfo {
        private Integer optionId;
        private Integer saleId;
        private String optionName;
        private Integer quantity;
        private Integer price;

        public static OrderOptionInfo fromEntity(OrderOption orderOption) {
            Option option = orderOption.getOption();

            return OrderOptionInfo.builder()
                    .optionId(option.getId())
                    .saleId(option.getSale().getId())
                    .optionName(option.getName())
                    .quantity(orderOption.getQuantity())
                    .price(orderOption.getPrice())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteReviewResponse {
        private Integer reviewId;
        private boolean deleted;

        public static DeleteReviewResponse success(Integer reviewId) {
            return DeleteReviewResponse.builder()
                    .reviewId(reviewId)
                    .deleted(true)
                    .build();
        }

        public static DeleteReviewResponse fail(Integer reviewId) {
            return DeleteReviewResponse.builder()
                    .reviewId(reviewId)
                    .deleted(false)
                    .build();
        }
    }
}