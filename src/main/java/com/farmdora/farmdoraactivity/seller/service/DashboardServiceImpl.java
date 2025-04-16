package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.Period;
import com.farmdora.farmdoraactivity.seller.dto.ProductRatioDTO;
import com.farmdora.farmdoraactivity.seller.dto.SalesOverviewDTO;
import com.farmdora.farmdoraactivity.seller.dto.StatusRatioDTO;
import com.farmdora.farmdoraactivity.seller.mapper.DashboardMapper;
import com.farmdora.farmdoraactivity.seller.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final DashboardMapper dashboardMapper;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSalesData(Integer sellerId, LocalDate startDate, LocalDate endDate, Period period) {
        List<SalesOverviewDTO> dailySales = findDailySalesBySellerId(sellerId, startDate, endDate);
        if (period.equals(Period.DAILY)) {
            return dashboardMapper.formatDailyData(dailySales);
        } else if (period.equals(Period.WEEKLY)) {
            return dashboardMapper.formatWeeklyData(dailySales);
        } else if (period.equals(Period.MONTHLY)) {
            return dashboardMapper.formatMonthlyData(dailySales);
        }
        return dashboardMapper.formatDailyData(dailySales);
    }

    private List<SalesOverviewDTO> findDailySalesBySellerId(Integer sellerId, LocalDate startDate, LocalDate endDate) {
        return dashboardRepository.findDailySalesBySellerId(sellerId, startDate, endDate.plusDays(1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductRatioDTO> findProductTypeCountBySellerId(Integer sellerId) {
        int totalOrder = dashboardRepository.countBySellerId(sellerId);
        List<ProductRatioDTO> productTypeCountRawData = dashboardRepository.findProductTypeCountBySellerId(sellerId);
        for (ProductRatioDTO productRatioDTO : productTypeCountRawData) {
            productRatioDTO.setPercentage(totalOrder > 0 ? (productRatioDTO.getCount() * 100.0) / totalOrder : 0.0);
        }

        return productTypeCountRawData;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusRatioDTO> findStatusTypeCountBySellerId(Integer sellerId) {
        int totalOrder = dashboardRepository.countBySellerId(sellerId);
        List<StatusRatioDTO> statusTypeCountRawData = dashboardRepository.findStatusTypeCountBySellerId(sellerId);
        for (StatusRatioDTO statusRatioDTO : statusTypeCountRawData) {
            statusRatioDTO.setPercentage(totalOrder > 0 ? statusRatioDTO.getCount() * 100.0 / totalOrder : 0.0);
        }

        return statusTypeCountRawData;
    }
}
