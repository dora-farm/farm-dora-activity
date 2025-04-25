package com.farmdora.farmdoraactivity.user.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {
    private int likeId;
    private int optionId;
    private int saleId;
    private String title;
    private String option;
    private Integer price;
    private String saveFile;
    private Double score;
    private Long reviewCount;

    public static WishlistDTO from(Object[] obj) {
        return WishlistDTO.builder()
                .likeId((Integer) obj[0])
                .optionId((Integer) obj[1])
                .saleId((Integer) obj[2])
                .title((String) obj[3])
                .option((String) obj[4])
                .price((Integer) obj[5])
                .saveFile((String) obj[6])
                .score((Double) obj[7])
                .reviewCount((Long) obj[8])
                .build();
    }
}