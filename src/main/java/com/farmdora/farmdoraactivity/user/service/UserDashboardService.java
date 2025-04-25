package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.user.dto.OrderStatusDTO;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO;
import com.farmdora.farmdoraactivity.user.dto.LikePreviewDTO;

import java.util.List;

public interface UserDashboardService {

    UserDashboardDTO getDashboardInfo(Integer userId);

    List<OrderStatusDTO> getOrderStatusByUserId(Integer userId);

    List<LikePreviewDTO> getWishPreviewByUserId(Integer userId);

}
