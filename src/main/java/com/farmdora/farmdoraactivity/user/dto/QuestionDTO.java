package com.farmdora.farmdoraactivity.user.dto;

import com.farmdora.farmdoraactivity.entity.BaseTimeEntity;
import com.farmdora.farmdoraactivity.entity.Question;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private int id;
    private String title;
    private LocalDateTime createDate;
    private boolean isProcess;

    public static QuestionDTO from(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .createDate(question.getCreatedDate())
                .isProcess(question.isProcess())
                .build();
    }


}
