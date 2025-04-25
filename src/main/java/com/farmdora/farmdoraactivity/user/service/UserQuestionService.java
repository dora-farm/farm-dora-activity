package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.entity.Question;
import com.farmdora.farmdoraactivity.user.dto.QuestionDTO;
import com.farmdora.farmdoraactivity.user.repository.UserQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQuestionService {
    private final UserQuestionRepository userQuestionRepository;

    public List<QuestionDTO> getQuestionByUserId(Integer userId) {
        List<Question> questions = userQuestionRepository.findAllByUser_UserIdOrderByCreatedDateDesc(userId);

        return questions.stream()
                .map(QuestionDTO::from)
                .collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionByPeriod(Integer userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Question> questions = userQuestionRepository.findQuestionByPeriod(userId, startDate, endDate);

        return questions.stream()
                .map(QuestionDTO::from)
                .collect(Collectors.toList());
    }
}
