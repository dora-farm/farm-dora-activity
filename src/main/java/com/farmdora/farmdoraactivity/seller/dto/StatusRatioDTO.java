package com.farmdora.farmdoraactivity.seller.dto;


import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatusRatioDTO {
    private String statusName;
    private Long count;
    private Double percentage;

    public StatusRatioDTO(String statusName, Long count) {
        this.statusName = statusName;
        this.count = count;
    }
}