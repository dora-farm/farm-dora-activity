package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.entity.Question;
import com.farmdora.farmdoraactivity.user.dto.QuestionDTO;
import com.farmdora.farmdoraactivity.user.repository.UserQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQuestionService {
    private final UserQuestionRepository userQuestionRepository;

    public List<QuestionDTO> getQuestionByUserId(Integer userId) {
        List<Question> questions = userQuestionRepository.findAllByUser_UserId(userId);

        return questions.stream()
                .map(QuestionDTO::from)
                .collect(Collectors.toList());
    }
}
