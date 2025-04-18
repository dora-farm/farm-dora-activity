package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.entity.User;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO;
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
    public UserDashboardDTO getDashboardInfo(@Param("userId") Integer userId) {
        User userInfo = userDashboardRepository.findById(userId).orElse(null);

        UserInfoDTO userInfoDTO = UserInfoDTO.from(userInfo);
        log.info(userInfoDTO.toString());

        Long totalAmount = userDashboardRepository.sumTotalAmount(userId);
        Long reviewCount = userDashboardRepository.countReviewsByUserId(userId);
        Long inquiryCount = userDashboardRepository.countInquiriesByUserId(userId);

        ActivityInfoDTO activityInfoDTO = ActivityInfoDTO.builder()
                .totalAmount(totalAmount != null ? totalAmount : 0L)
                .reviewCount(reviewCount != null ? reviewCount : 0L)
                .inquiryCount(inquiryCount != null ? inquiryCount : 0L)
                .build();
        log.info(activityInfoDTO.toString());

        return UserDashboardDTO.builder()
                .userInfoDTO(userInfoDTO)
                .activityInfoDTO(activityInfoDTO)
                .build();
    }
}
