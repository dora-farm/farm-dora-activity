package com.farmdora.farmdoraactivity.admin.service;

import com.farmdora.farmdoraactivity.admin.dto.OrderDTO;
import com.farmdora.farmdoraactivity.admin.repository.AdminJoinsRepository;
import com.farmdora.farmdoraactivity.admin.repository.AdminSalesRepository;
import com.farmdora.farmdoraactivity.seller.dto.Period;
import com.farmdora.farmdoraactivity.seller.mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final AdminJoinsRepository adminJoinsRepository;
    private final AdminSalesRepository adminSalesRepository;
    private final DashboardMapper dashboardMapper;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSalesData(LocalDate startDate, LocalDate endDate, Period period) {
        List<OrderDTO> findAllSumPrice = adminSalesRepository.findAllSumPrice(startDate, endDate);
        if (period.equals(Period.DAILY)) {
            return dashboardMapper.formatDailyData(findAllSumPrice)
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUsersData(LocalDate startDate, LocalDate endDate, Period period) {

    }
}
