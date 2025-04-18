package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO.*;
import org.springframework.data.repository.query.Param;

public interface UserDashboardService {

    UserInfoDTO findUserInfo(@Param("userId") Integer userId);
}
