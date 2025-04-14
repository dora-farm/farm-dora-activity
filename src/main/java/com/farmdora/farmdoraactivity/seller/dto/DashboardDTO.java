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

        public static SalesOverviewDTO from(OrderOption orderOption) {
            return SalesOverviewDTO.builder()
                    .createdDate(orderOption.getOrder().getCreatedDate().toLocalDate())
                    .price(orderOption.getPrice())
                    .sellerId(orderOption.getOptions().getSale().getSeller().getId())
                    .build();
        }
    }
}
