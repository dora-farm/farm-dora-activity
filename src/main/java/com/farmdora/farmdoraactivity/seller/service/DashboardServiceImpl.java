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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public List<SalesOverviewDTO> findDailySalesBySellerId(Integer sellerId, LocalDate startDate, LocalDate endDate) {

        List<Object[]> dailySalesRawData = dashboardRepository.findDailySalesBySellerId(sellerId, startDate, endDate);

        return dailySalesRawData
                .stream()
                .map(row -> new SalesOverviewDTO(
                        (LocalDate) row[0],
                        (Integer) row[1],
                        (Integer) row[2]
                ))
                .collect(Collectors.toList());
    }


}
