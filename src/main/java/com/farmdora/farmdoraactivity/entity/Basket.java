package com.farmdora.farmdoraactivity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Integer id;

    @Column(nullable = false)
    private Integer user_id;

    @Column(nullable = false)
    private Integer option_id;

    @Column(nullable = false)
    private Integer quantity;
}

