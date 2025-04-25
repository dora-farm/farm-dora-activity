package com.farmdora.farmdoraactivity.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LikePreviewDTO {

    private int saleId;
    private String title;
    private String option;
    private Integer price;
    private String saveFile;

    public static LikePreviewDTO from(Object[] obj) {
        return LikePreviewDTO.builder()
                .saleId((Integer) obj[0])
                .title((String) obj[1])
                .option((String) obj[2])
                .price((Integer) obj[3])
                .saveFile((String) obj[4])
                .build();
    }
}
