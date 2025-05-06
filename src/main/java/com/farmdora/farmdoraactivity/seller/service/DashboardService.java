package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.Period;
import com.farmdora.farmdoraactivity.seller.dto.ProductRatioDTO;
import com.farmdora.farmdoraactivity.seller.dto.StatusRatioDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DashboardService {
    Map<String, Object> getSalesData(Integer userId, LocalDate startDate, LocalDate endDate, Period period);

    List<ProductRatioDTO> findProductTypeCountBySellerId(Integer userId);

    List<StatusRatioDTO> findStatusTypeCountBySellerId(Integer userId);
}
