package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DashboardService {
    List<SalesOverviewDTO> findDailySalesBySellerId(Integer sellerId, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getSalesData(Integer sellerId, LocalDate startDate, LocalDate endDate, String period);
}
