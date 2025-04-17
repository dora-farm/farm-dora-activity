package com.farmdora.farmdoraactivity.admin.service;

import com.farmdora.farmdoraactivity.seller.dto.Period;

import java.time.LocalDate;
import java.util.Map;

public interface AdminDashboardService {

    Map<String, Object> getSalesData(LocalDate startDate, LocalDate endDate, Period period);

    Map<String, Object> getUsersData(LocalDate startDate, LocalDate endDate, Period period);
}
