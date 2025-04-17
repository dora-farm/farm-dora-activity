package com.farmdora.farmdoraactivity.admin.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private LocalDate createdDate;
    private Long totalPrice;
}
