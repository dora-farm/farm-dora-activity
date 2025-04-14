package com.farmdora.farmdoraactivity.seller.controller;

import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.SalesOverviewDTO;
import com.farmdora.farmdoraactivity.seller.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/my/seller")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard/sales")
    public ResponseEntity<Map<String, Object>> getDailySales(
            @RequestParam Integer sellerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<SalesOverviewDTO> salesData = dashboardService.findDailySalesBySellerId(sellerId, startDate, endDate);

        // DTO를 차트 데이터 형식으로 변환
        String[] labels = salesData.stream()
                .map(sale -> sale.getCreatedDate().toString())
                .toArray(String[]::new);

        int[] data = salesData.stream()
                .mapToInt(SalesOverviewDTO::getPrice)
                .toArray();

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    // 제품별 매출 비율과 반품/교환율을 위한 추가 엔드포인트들
    @GetMapping("/dashboard/product-ratio")
    public ResponseEntity<Map<String, Object>> getProductSalesRatio(@RequestParam Integer sellerId) {
        // 제품 카테고리별 판매 비율 데이터를 가져오는 서비스 메서드 호출
        // 예시 데이터
        Map<String, Object> response = new HashMap<>();
        response.put("labels", new String[] {"과일", "채소", "곡류"});
        response.put("data", new int[] {40, 30, 30});

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/return-ratio")
    public ResponseEntity<Map<String, Object>> getReturnRatio(@RequestParam Integer sellerId) {
        // 반품/교환 비율 데이터를 가져오는 서비스 메서드 호출
        // 예시 데이터
        Map<String, Object> response = new HashMap<>();
        response.put("labels", new String[] {"정상", "반품", "교환"});
        response.put("data", new int[] {60, 10, 30});

        return ResponseEntity.ok(response);
    }
}