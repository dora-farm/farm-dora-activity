package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DashboardService {
    Map<String, Object> getSalesData(Integer sellerId, LocalDate startDate, LocalDate endDate, String period);

    List<SalesOverviewDTO> findDailySalesBySellerId(Integer sellerId, LocalDate startDate, LocalDate endDate);

    List<ProductRatioDTO> findProductTypeCountBySellerId(Integer sellerId);
}
