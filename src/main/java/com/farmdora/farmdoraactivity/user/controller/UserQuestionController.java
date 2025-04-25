package com.farmdora.farmdoraactivity.user.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.user.dto.QuestionDTO;
import com.farmdora.farmdoraactivity.user.service.UserQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_QUESTIONINFO_SUCCESS;

@RestController
@RequestMapping("/api/my/user/question")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class UserQuestionController {
    private final UserQuestionService userQuestionService;

    @GetMapping("/list")
    public ResponseEntity<?> getQuestionByUserId(@RequestParam Integer userId) {

        List<QuestionDTO> questions = userQuestionService.getQuestionByUserId(userId);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_QUESTIONINFO_SUCCESS.getMessage(), questions));

    }
}
