package com.farmdora.farmdoraactivity.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO {
    private short statusId;
    private Long statusCount;
}
