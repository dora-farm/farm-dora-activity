package com.farmdora.farmdoraactivity.seller.dto;

import com.farmdora.farmdoraactivity.entity.Address;
import lombok.*;


@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private String userName;
    private String phoneNum;
    private Address address;

    public static OrderDetailDTO from (Object[] obj) {
        return OrderDetailDTO.builder()
                .userName((String) obj[0])
                .phoneNum((String) obj[1])
                .address((Address) obj[2])
                .build();
    }

}
