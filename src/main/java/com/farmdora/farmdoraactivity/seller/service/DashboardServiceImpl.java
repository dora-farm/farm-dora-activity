package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.*;
import com.farmdora.farmdoraactivity.seller.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    private Map<String, Object> formatDailyData(List<SalesOverviewDTO> dailySales) {

        String[] labels = dailySales.stream()
                .map(sale -> sale.getCreatedDate().toString())
                .toArray(String[]::new);

        int[] data = dailySales.stream()
                .mapToInt(SalesOverviewDTO::getPrice)
                .toArray();

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", data);

        return response;
    }

    private Map<String, Object> formatWeeklyData(List<SalesOverviewDTO> dailySales) {

        Map<String, Integer> weeklyData = dailySales
                .stream()
                .collect(Collectors.groupingBy(
                        sale -> {
                            LocalDate date = sale.getCreatedDate();
                            int month = date.getMonthValue();

                            LocalDate firstDayOfMonth = date.withDayOfMonth(1);
                            int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
                            int weekOfMonth = (date.getDayOfMonth() - 1 + firstDayOfWeek - 1) / 7 + 1;

                            return String.format("%d월 %d주차", month, weekOfMonth);
                        },
                        Collectors.summingInt(SalesOverviewDTO::getPrice)
                ));

        // 월 순서대로 정렬하기 위한 커스텀 정렬 로직
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(weeklyData.entrySet());
        sortedEntries.sort((e1, e2) -> {
            // "월월 주주차" 형식을 파싱하여 정렬
            String[] parts1 = e1.getKey().split("월 |주차");
            String[] parts2 = e2.getKey().split("월 |주차");

            int month1 = Integer.parseInt(parts1[0]);
            int week1 = Integer.parseInt(parts1[1]);
            int month2 = Integer.parseInt(parts2[0]);
            int week2 = Integer.parseInt(parts2[1]);

            if (month1 != month2) {
                return month1 - month2; // 월 기준 정렬
            } else {
                return week1 - week2; // 같은 월이면 주차 기준 정렬
            }
        });

        // 응답 데이터 형식으로 변환
        String[] labels = sortedEntries.stream()
                .map(Map.Entry::getKey)
                .toArray(String[]::new);

        int[] data = sortedEntries.stream()
                .mapToInt(Map.Entry::getValue)
                .toArray();

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", data);

        return response;
    }

    private Map<String, Object> formatMonthlyData(List<SalesOverviewDTO> dailySales) {
        // 월별로 그룹화하여 합계 계산
        Map<String, Integer> monthlyData = dailySales.stream()
                .collect(Collectors.groupingBy(
                        sale -> {
                            LocalDate date = sale.getCreatedDate();
                            return String.format("%d-%02d", date.getYear(), date.getMonthValue());
                        },
                        Collectors.summingInt(SalesOverviewDTO::getPrice)
                ));

        // 날짜순으로 정렬
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(monthlyData.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());

        // 응답 데이터 형식으로 변환
        String[] labels = sortedEntries.stream()
                .map(Map.Entry::getKey)
                .toArray(String[]::new);

        int[] data = sortedEntries.stream()
                .mapToInt(Map.Entry::getValue)
                .toArray();

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", data);

        return response;
    }

    @Override
    public Map<String, Object> getSalesData(Integer sellerId, LocalDate startDate, LocalDate endDate, String period) {
        List<SalesOverviewDTO> dailySales = findDailySalesBySellerId(sellerId, startDate, endDate);

        // 요청된 기간에 따라 데이터 가공
        if ("daily".equals(period)) {
            return formatDailyData(dailySales);
        } else if ("weekly".equals(period)) {
            return formatWeeklyData(dailySales);
        } else if ("monthly".equals(period)) {
            return formatMonthlyData(dailySales);
        }

        // 기본값은 일별 데이터
        return formatDailyData(dailySales);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesOverviewDTO> findDailySalesBySellerId(Integer sellerId, LocalDate startDate, LocalDate endDate) {

        List<Object[]> salesRawData = dashboardRepository.findDailySalesBySellerId(sellerId, startDate, endDate);

        // DB에서 가져온 데이터를 날짜를 키로 하는 맵으로 변환
        Map<LocalDate, Integer> salesByDate = salesRawData
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
