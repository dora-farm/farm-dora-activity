package com.farmdora.farmdoraactivity.seller.controller;

import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.SalesOverviewDTO;
import com.farmdora.farmdoraactivity.seller.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
}