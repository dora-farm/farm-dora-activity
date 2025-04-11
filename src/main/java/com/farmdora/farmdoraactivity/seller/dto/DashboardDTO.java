package com.farmdora.farmdoraactivity.seller.dto;

import com.farmdora.farmdoraactivity.entity.Order;
import com.farmdora.farmdoraactivity.entity.OrderOption;
import lombok.*;

import java.time.LocalDateTime;

public class DashboardDTO {


    @Setter
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SalesOverviewDTO {
        private LocalDateTime createdDate;
        private int price;

        public static SalesOverviewDTO from(OrderOption orderOption, Order order) {
            return SalesOverviewDTO.builder()
                    .createdDate(order.getCreatedDate())
                    .price(orderOption.getPrice())
                    .build();
        }
    }
}
