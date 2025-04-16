package com.farmdora.farmdoraactivity.seller.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.seller.dto.Period;
import com.farmdora.farmdoraactivity.seller.dto.ProductRatioDTO;
import com.farmdora.farmdoraactivity.seller.dto.StatusRatioDTO;
import com.farmdora.farmdoraactivity.seller.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.*;

@RestController
@RequestMapping("/api/my/seller/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/sales")
    public ResponseEntity<?> getSales(
            @RequestParam Integer sellerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "DAILY") Period period) {
        Map<String, Object> result = dashboardService.getSalesData(sellerId, startDate, endDate, period);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_SALESTATUS_SUCCESS.getMessage(), result));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatusRatio(@RequestParam Integer sellerId) {
        List<StatusRatioDTO> result = dashboardService.findStatusTypeCountBySellerId(sellerId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_STATUSRATIO_SUCCESS.getMessage(), result));
    }

    @GetMapping("/product")
    public ResponseEntity<?> getProductRatio(@RequestParam Integer sellerId) {
        List<ProductRatioDTO> result = dashboardService.findProductTypeCountBySellerId(sellerId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_PRODUCTRATIO_SUCCESS.getMessage(), result));
    }
}