package com.farmdora.farmdoraactivity.user.dto;

import com.farmdora.farmdoraactivity.entity.User;
import lombok.*;

public class UserDashboardDTO {

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDTO {
        private String name;
        private String phone;
        private String email;

        public static UserInfoDTO from(User user) {
            return UserInfoDTO.builder()
                    .name(user.getName())
                    .phone(user.getPhoneNum())
                    .email(user.getEmail())
                    .build();
        }
    }
}
