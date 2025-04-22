package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.user.dto.OrderStatusDTO;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO;
import com.farmdora.farmdoraactivity.user.dto.WishlistDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDashboardService {

    UserDashboardDTO getDashboardInfo(@Param("userId") Integer userId);

    List<OrderStatusDTO> getOrderStatusByUserId(@Param("userId") Integer userId);

    List<WishlistDTO> getWishlistByUserId(@Param("userId") Integer userId);
}
