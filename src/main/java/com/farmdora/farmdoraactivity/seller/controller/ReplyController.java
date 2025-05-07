package com.farmdora.farmdoraactivity.seller.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.seller.dto.QuestionDTO;
import com.farmdora.farmdoraactivity.seller.dto.ReviewDTO;
import com.farmdora.farmdoraactivity.seller.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.*;


@RestController
@RequestMapping("/api/my/seller/order")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    @PutMapping("/review/update")
    public ResponseEntity<?> updateReviewReply(@RequestBody ReviewDTO reviewDTO) {

        replyService.updateReviewReply(reviewDTO);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, REVIEW_UPDATE_SUCCESS.getMessage(), reviewDTO));
    }

    @PostMapping("/review/insert")
    public ResponseEntity<?> insertReviewReply(@RequestBody ReviewDTO reviewDTO) {
        replyService.insertReviewReply(reviewDTO);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, REVIEW_INSERT_SUCCESS.getMessage(), reviewDTO));
    }

    @DeleteMapping("/review/delete")
    public ResponseEntity<?> deleteReviewReply(@RequestParam Integer reviewId) {
        replyService.deleteReviewReply(reviewId);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, REVIEW_DELETE_SUCCESS.getMessage(), reviewId));
    }

    @PutMapping("/question/update")
    public ResponseEntity<?> updateQuestionReply(@RequestBody QuestionDTO questionDTO) {
        replyService.updateQuestionReply(questionDTO);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, QUESTION_UPDATE_SUCCESS.getMessage(), questionDTO));
    }

    @PostMapping("/question/insert")
    public ResponseEntity<?> insertQuestionReply(@RequestBody QuestionDTO questionDTO) {
        replyService.insertQuestionReply(questionDTO);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, QUESTION_INSERT_SUCCESS.getMessage(), questionDTO));
    }

    @DeleteMapping("/question/delete")
    public ResponseEntity<?> deleteQuestionReply(@RequestParam Integer questionId) {
        replyService.deleteQuestionReply(questionId);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, QUESTION_DELETE_SUCCESS.getMessage(), questionId));
    }
}
