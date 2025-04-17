package com.farmdora.farmdoraactivity.admin.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserDTO {
    private LocalDate createdDate;
    private Long totalUsers;

    public UserDTO(java.sql.Date createdDate, Long totalUsers) {
        this.createdDate = createdDate != null ? createdDate.toLocalDate() : null;
        this.totalUsers = totalUsers != null ? totalUsers : 0;
    }
}
