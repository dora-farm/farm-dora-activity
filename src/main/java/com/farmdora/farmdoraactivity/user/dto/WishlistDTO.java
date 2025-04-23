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
                .saleId((Integer) obj[1])
                .title((String) obj[2])
                .option((String) obj[3])
                .price((Integer) obj[4])
                .saveFile((String) obj[5])
                .score((Double) obj[6])
                .reviewCount((Long) obj[7])
                .build();
    }
}