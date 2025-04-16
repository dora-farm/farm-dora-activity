package com.farmdora.farmdoraactivity.seller.dto;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRatioDTO {
    private String typename;
    private Long count;
    private Double percentage;

    public ProductRatioDTO(String typename, Long count) {
        this.typename = typename;
        this.count = count;
    }
}
