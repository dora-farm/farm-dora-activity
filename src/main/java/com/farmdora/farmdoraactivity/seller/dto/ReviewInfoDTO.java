package com.farmdora.farmdoraactivity.seller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInfoDTO {
    private String saveFile;
    private Integer orderId;

    public static ReviewInfoDTO from(Object[] obj) {
        return ReviewInfoDTO.builder()
                .saveFile((String) obj[0])
                .orderId((Integer) obj[1])
                .build();
    }
}
