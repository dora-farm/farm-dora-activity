package com.farmdora.farmdoraactivity.seller.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RefundInfoDTO {
    private LocalDateTime createdDate;
    private String typeName;
    private String content;
    private String saveFile;

    public static RefundInfoDTO from (Object[] obj) {
        return RefundInfoDTO.builder()
                .createdDate((LocalDateTime) obj[0])
                .typeName((String) obj[1])
                .content((String) obj[2])
                .saveFile((String) obj[3])
                .build();
    }

}
