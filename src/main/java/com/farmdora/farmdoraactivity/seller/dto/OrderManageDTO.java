package com.farmdora.farmdoraactivity.seller.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderManageDTO {
    private int orderId;
    private String productName;
    private String OptionName;
    private LocalDateTime orderDate;
    private String userName;
    private Integer price;
    private String statusName;

    public static OrderManageDTO from (Object[] obj) {
        return OrderManageDTO.builder()
                .orderId((Integer) obj[0])
                .productName((String) obj[1])
                .OptionName((String) obj[2])
                .orderDate((LocalDateTime) obj[3])
                .userName((String) obj[4])
                .price((Integer) obj[5])
                .statusName((String) obj[6])
                .build();
    }
}
