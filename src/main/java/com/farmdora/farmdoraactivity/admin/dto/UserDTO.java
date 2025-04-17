package com.farmdora.farmdoraactivity.admin.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private LocalDate createdDate;
    private Long totalUsers;
}
