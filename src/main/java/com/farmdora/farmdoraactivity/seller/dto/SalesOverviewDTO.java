package com.farmdora.farmdoraactivity.seller.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalesOverviewDTO {
    private LocalDate createdDate;
    private Long price;
    private Integer sellerId;

    public SalesOverviewDTO(java.sql.Date createdDate, Long price, Integer sellerId) {
        this.createdDate = createdDate != null ? createdDate.toLocalDate() : null;
        this.price = price != null ? price : 0;
        this.sellerId = sellerId;
    }
}
