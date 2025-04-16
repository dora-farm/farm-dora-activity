package com.farmdora.farmdoraactivity.seller.dto;

import com.farmdora.farmdoraactivity.entity.OrderOption;
import lombok.*;

import java.time.LocalDate;

public class DashboardDTO {

    @Setter
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SalesOverviewDTO {
        private LocalDate createdDate;
        private int price;
        private Integer sellerId;
    }

    @Setter
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductRatioDTO {
        private String typename;
        private int count;
        private Double percentage;
    }
}
