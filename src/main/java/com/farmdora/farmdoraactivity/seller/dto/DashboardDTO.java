package com.farmdora.farmdoraactivity.seller.dto;

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
}
