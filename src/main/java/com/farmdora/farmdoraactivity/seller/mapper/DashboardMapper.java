package com.farmdora.farmdoraactivity.seller.mapper;

import com.farmdora.farmdoraactivity.seller.dto.SalesOverviewDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DashboardMapper {

    public Map<String, Object> formatDailyData(List<SalesOverviewDTO> dailySales) {

        String[] dates = dailySales.stream()
                .map(sale -> sale.getCreatedDate().toString())
                .toArray(String[]::new);

        long[] prices = dailySales.stream()
                .mapToLong(SalesOverviewDTO::getPrice)
                .toArray();

        Map<String, Object> response = new HashMap<>();
        response.put("labels", dates);
        response.put("data", prices);

        return response;
    }

    public Map<String, Object> formatWeeklyData(List<SalesOverviewDTO> dailySales) {

        Map<String, Long> weeklyData = dailySales
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
                        Collectors.summingLong(SalesOverviewDTO::getPrice)
                ));

        // 월 순서대로 정렬하기 위한 커스텀 정렬 로직
        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(weeklyData.entrySet());
        sortedEntries.sort((e1, e2) -> {
            // "n월 n주차" 형식을 파싱하여 정렬
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
        String[] dates = sortedEntries.stream()
                .map(Map.Entry::getKey)
                .toArray(String[]::new);

        long[] prices = sortedEntries.stream()
                .mapToLong(Map.Entry::getValue)
                .toArray();

        Map<String, Object> response = new HashMap<>();
        response.put("labels", dates);
        response.put("data", prices);

        return response;
    }

    public Map<String, Object> formatMonthlyData(List<SalesOverviewDTO> dailySales) {
        // 월별로 그룹화하여 합계 계산
        Map<String, Long> monthlyData = dailySales.stream()
                .collect(Collectors.groupingBy(
                        sale -> {
                            LocalDate date = sale.getCreatedDate();
                            return String.format("%d-%02d", date.getYear(), date.getMonthValue());
                        },
                        Collectors.summingLong(SalesOverviewDTO::getPrice)
                ));

        // 날짜순으로 정렬
        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(monthlyData.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());

        // 응답 데이터 형식으로 변환
        String[] dates = sortedEntries.stream()
                .map(Map.Entry::getKey)
                .toArray(String[]::new);

        long[] prices = sortedEntries.stream()
                .mapToLong(Map.Entry::getValue)
                .toArray();

        Map<String, Object> response = new HashMap<>();
        response.put("labels", dates);
        response.put("data", prices);

        return response;
    }


}
