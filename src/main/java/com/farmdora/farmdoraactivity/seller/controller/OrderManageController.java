package com.farmdora.farmdoraactivity.seller.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.seller.dto.OrderDetailDTO;
import com.farmdora.farmdoraactivity.seller.dto.QuestionInfoDTO;
import com.farmdora.farmdoraactivity.seller.dto.RefundInfoDTO;
import com.farmdora.farmdoraactivity.seller.dto.ReviewInfoDTO;
import com.farmdora.farmdoraactivity.seller.service.OrderManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.*;

@RestController
@RequestMapping("/api/my/seller/order")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class OrderManageController {
    private final OrderManageService orderManageService;

    @GetMapping
    public ResponseEntity<?> getOrderManageCount(Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_ORDERMANAGECOUNT_SUCCESS.getMessage(),
                        orderManageService.getAllStatistics(userId)));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserInfo(@RequestParam Integer orderId) {
        OrderDetailDTO details = orderManageService.getDetailInfo(orderId);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_ORDERDETAILINFO_SUCCESS.getMessage(), details));
    }

    @GetMapping("/refund")
    public ResponseEntity<?> getRefundInfo(@RequestParam Integer orderId) {
        List<RefundInfoDTO> refundInfo = orderManageService.getRefundInfo(orderId);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_REFUNDINFO_SUCCESS.getMessage(), refundInfo));
    }

    @GetMapping("/review")
    public ResponseEntity<?> getReviewInfo(@RequestParam Integer reviewId) {
        List<ReviewInfoDTO> reviewInfo = orderManageService.getReviewInfo(reviewId);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_REVIEWINFO_SUCCESS.getMessage(), reviewInfo));
    }

    @GetMapping("/question")
    public ResponseEntity<?> getQuestionInfo(@RequestParam Integer questionId) {
        List<QuestionInfoDTO> questionAnswer = orderManageService.getQuestionAnswer(questionId);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_QUESTIONANSWER_SUCCESS.getMessage(), questionAnswer));
    }
}
