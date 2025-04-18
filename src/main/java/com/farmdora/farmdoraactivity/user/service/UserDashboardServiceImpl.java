package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.entity.User;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO.*;
import com.farmdora.farmdoraactivity.user.repository.UserDashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserDashboardServiceImpl implements UserDashboardService {

    private final UserDashboardRepository userDashboardRepository;

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO findUserInfo(@Param("userId") Integer userId) {
        User userInfo = userDashboardRepository.findById(userId).orElse(null);
        UserInfoDTO dto = UserInfoDTO.from(userInfo);
        return dto;
    }
}
