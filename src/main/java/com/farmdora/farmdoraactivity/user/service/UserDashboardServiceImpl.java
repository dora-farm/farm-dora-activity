package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.common.exception.ResourceNotFoundException;
import com.farmdora.farmdoraactivity.entity.User;
import com.farmdora.farmdoraactivity.user.dto.OrderStatusDTO;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO.*;
import com.farmdora.farmdoraactivity.user.dto.WishPreviewDTO;
import com.farmdora.farmdoraactivity.user.dto.WishlistDTO;
import com.farmdora.farmdoraactivity.user.repository.UserDashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserDashboardServiceImpl implements UserDashboardService {

    private final UserDashboardRepository userDashboardRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDashboardDTO getDashboardInfo(Integer userId) {

        User userInfo = userDashboardRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", userId));
        UserInfoDTO userInfoDTO = UserInfoDTO.from(userInfo);

        Long totalAmount = userDashboardRepository.sumTotalAmount(userId);
        Long reviewCount = userDashboardRepository.countReviewsByUserId(userId);
        Long inquiryCount = userDashboardRepository.countInquiriesByUserId(userId);
        ActivityInfoDTO activityInfoDTO = ActivityInfoDTO.builder()
                .totalAmount(totalAmount != null ? totalAmount : 0L)
                .reviewCount(reviewCount != null ? reviewCount : 0L)
                .inquiryCount(inquiryCount != null ? inquiryCount : 0L)
                .build();

        return UserDashboardDTO.builder()
                .userInfoDTO(userInfoDTO)
                .activityInfoDTO(activityInfoDTO)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusDTO> getOrderStatusByUserId(Integer userId) {

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return userDashboardRepository.findOrderStatusByUserId(userId, startDateTime, endDateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishPreviewDTO> getWishPreviewByUserId(Integer userId) {

        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Object[]> results = userDashboardRepository.findWishPreviewByUserId(userId, pageRequest);
        log.info("마이페이지 찜리스트:  {}", results);

        return results.stream()
                .map(WishPreviewDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishlistDTO> getWishlistByUserId(Integer userId) {

        List<Object[]> results = userDashboardRepository.findWishlistByUserId(userId);
        log.info("찜리스트: {}", results);

        return results.stream()
                .map(WishlistDTO::from)
                .collect(Collectors.toList());
    }
}