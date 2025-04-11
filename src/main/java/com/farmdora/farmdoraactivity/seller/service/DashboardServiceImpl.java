package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.entity.OrderOption;
import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.*;
import com.farmdora.farmdoraactivity.seller.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    List<SalesOverviewDTO> findDailySalesBySellerId(Integer sellerId, LocalDate startDate, LocalDate endDate) {

        List<OrderOption> orderOptions = dashboardRepository.findDailySalesBySellerId(sellerId, startDate, endDate);

        List<SalesOverviewDTO> salesOverviewList = new ArrayList<>();
    }


}
