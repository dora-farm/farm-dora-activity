package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.*;
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

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public List<SalesOverviewDTO> findDailySalesBySellerId(Integer sellerId, LocalDate startDate, LocalDate endDate) {

        List<Object[]> dailySalesRawData = dashboardRepository.findDailySalesBySellerId(sellerId, startDate, endDate);

        // DB에서 가져온 데이터를 날짜를 키로 하는 맵으로 변환
        Map<LocalDate, Integer> salesByDate = dailySalesRawData
                .stream()
                .collect(Collectors.toMap(
                        row -> ((java.sql.Date) row[0]).toLocalDate(),
                        row -> {
                            Long sumPrice = (Long) row[1];
                            return sumPrice != null ? sumPrice.intValue() : 0;
                        }
                ));

        // startDate부터 endDate까지의 모든 날짜를 포함하는 결과 생성
        List<SalesOverviewDTO> result = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // 해당 날짜의 매출이 있으면 그 값을, 없으면 0을 사용
            int salesAmount = salesByDate.getOrDefault(date, 0);
            result.add(new SalesOverviewDTO(date, salesAmount, sellerId));
        }

        return result;
    }


}
