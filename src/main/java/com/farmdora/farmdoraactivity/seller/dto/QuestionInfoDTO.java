package com.farmdora.farmdoraactivity.seller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoDTO {
    private String content;
    private String answer;

    public static QuestionInfoDTO from (Object[] obj) {
        return QuestionInfoDTO.builder()
                .content((String) obj[0])
                .answer((String) obj[1])
                .build();
    }
}