package com.farmdora.farmdoraactivity.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Depot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "depot_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String deliveryName;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String phoneNum;

    @Embedded
    private Address address;

    private String require;

    @Column(nullable = false)
    private boolean isDefault;
}
