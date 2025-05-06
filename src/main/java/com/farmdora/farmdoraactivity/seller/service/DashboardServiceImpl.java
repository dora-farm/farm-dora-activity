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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final DashboardMapper dashboardMapper;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSalesData(Integer userId, LocalDate startDate, LocalDate endDate, Period period) {
        List<SalesOverviewDTO> dailySales = findDailySalesBySellerId(userId, startDate, endDate);
        if (period.equals(Period.DAILY)) {
            return dashboardMapper.formatDailyData(dailySales);
        } else if (period.equals(Period.WEEKLY)) {
            return dashboardMapper.formatWeeklyData(dailySales);
        } else if (period.equals(Period.MONTHLY)) {
            return dashboardMapper.formatMonthlyData(dailySales);
        }
        return dashboardMapper.formatDailyData(dailySales);
    }

    private List<SalesOverviewDTO> findDailySalesBySellerId(Integer userId, LocalDate startDate, LocalDate endDate) {
        List<SalesOverviewDTO> salesDate = dashboardRepository.findDailySalesBySellerId(userId, startDate, endDate);

        Map<LocalDate, SalesOverviewDTO> salesByDate = salesDate.stream()
                .collect(Collectors.toMap(SalesOverviewDTO::getCreatedDate, dto -> dto));

        List<SalesOverviewDTO> result = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            SalesOverviewDTO dto = salesByDate.get(currentDate);
            if (dto == null) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
                dto = new SalesOverviewDTO(sqlDate, 0L, userId);
            }
            result.add(dto);
            currentDate = currentDate.plusDays(1);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductRatioDTO> findProductTypeCountBySellerId(Integer userId) {
        int totalOrder = dashboardRepository.countBySellerId(userId);
        List<ProductRatioDTO> productTypeCountRawData = dashboardRepository.findProductTypeCountBySellerId(userId);
        for (ProductRatioDTO productRatioDTO : productTypeCountRawData) {
            productRatioDTO.setPercentage(totalOrder > 0 ? (productRatioDTO.getCount() * 100.0) / totalOrder : 0.0);
        }

        return productTypeCountRawData;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusRatioDTO> findStatusTypeCountBySellerId(Integer userId) {
        int totalOrder = dashboardRepository.countBySellerId(userId);
        List<StatusRatioDTO> statusTypeCountRawData = dashboardRepository.findStatusTypeCountBySellerId(userId);
        for (StatusRatioDTO statusRatioDTO : statusTypeCountRawData) {
            statusRatioDTO.setPercentage(totalOrder > 0 ? statusRatioDTO.getCount() * 100.0 / totalOrder : 0.0);
        }

        return statusTypeCountRawData;
    }
}
