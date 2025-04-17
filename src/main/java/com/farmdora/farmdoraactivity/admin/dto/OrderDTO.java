package com.farmdora.farmdoraactivity.admin.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class OrderDTO {
    private LocalDate createdDate;
    private Long totalPrice;

    public OrderDTO(java.sql.Date createdDate, Long totalPrice) {
        this.createdDate = createdDate != null ? createdDate.toLocalDate() : null;
        this.totalPrice = totalPrice != null ? totalPrice : 0;
    }
}
