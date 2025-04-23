package com.farmdora.farmdoraactivity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private String addr;

    private String detailAddr;

    @Column(length = 5)
    private String postNum;
}
