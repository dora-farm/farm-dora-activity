package com.farmdora.farmdoraactivity.seller.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDTO {
    private int orderId;
    private String title;
    private String optionName;
    private LocalDateTime createdDate;
    private String userName;
    private Integer price;
    private String status;

    public static OrderListDTO from (Object[] obj) {
        return OrderListDTO.builder()
                .orderId((Integer) obj[0])
                .title((String) obj[1])
                .optionName((String) obj[2])
                .createdDate((LocalDateTime) obj[3])
                .userName((String) obj[4])
                .price((Integer) obj[5])
                .status((String) obj[6])
                .build();
    }

}
