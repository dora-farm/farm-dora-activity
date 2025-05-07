package com.farmdora.farmdoraactivity.user.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.user.dto.QuestionDTO;
import com.farmdora.farmdoraactivity.user.service.UserQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_QUESTIONINFO_SUCCESS;

@RestController
@RequestMapping("${api.prefix}/my/user/question")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class UserQuestionController {
    private final UserQuestionService userQuestionService;

    @GetMapping
    public ResponseEntity<?> getQuestions(
            Principal principal,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        Integer userId = Integer.parseInt(principal.getName());

        List<QuestionDTO> questions = userQuestionService.getQuestions(userId, startDate, endDate);
        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_QUESTIONINFO_SUCCESS.getMessage(), questions));
    }
}
