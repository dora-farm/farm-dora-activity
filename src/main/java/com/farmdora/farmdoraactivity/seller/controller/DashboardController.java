package com.farmdora.farmdoraactivity.seller.controller;

import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.*;
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
@RequestMapping("/api/my/seller/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    // 새로운 통합 엔드포인트
    @GetMapping("/sales")
    public ResponseEntity<Map<String, Object>> getSales(
            @RequestParam Integer sellerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "daily") String period) {

        // 서비스 메서드가 이미 Map<String, Object> 타입을 반환하므로 바로 사용
        Map<String, Object> response = dashboardService.getSalesData(sellerId, startDate, endDate, period);
        return ResponseEntity.ok(response);
    }

//    // 기존 엔드포인트는 하위 호환성을 위해 유지 (선택사항)
//    @GetMapping("/daily-sales")
//    public ResponseEntity<Map<String, Object>> getDailySales(
//            @RequestParam Integer sellerId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//
//        // 새로운 서비스 메서드를 호출하면서 daily 기간 지정
//        Map<String, Object> response = dashboardService.getSalesData(sellerId, startDate, endDate, "daily");
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/product")
    public List<ProductRatioDTO> getProductRatio(@RequestParam Integer sellerId) {
        return dashboardService.findProductTypeCountBySellerId(sellerId);
    }
}