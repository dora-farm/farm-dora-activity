package com.farmdora.farmdoraactivity.admin.controller;

import com.farmdora.farmdoraactivity.admin.service.AdminDashboardService;
import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.seller.dto.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_TOTALSALES_SUCCESS;
import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_TOTALUSERS_SUCCESS;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/sales")
    public ResponseEntity<?> adminGetSales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "DAILY") Period period) {
        Map<String, Object> result = adminDashboardService.getSalesData(startDate, endDate, period);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_TOTALSALES_SUCCESS.getMessage(), result));
    }

    @GetMapping("/joins")
    public ResponseEntity<?> adminGetJoins(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "DAILY") Period period) {
        Map<String, Object> result = adminDashboardService.getUsersData(startDate, endDate, period);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_TOTALUSERS_SUCCESS.getMessage(), result));
    }
}
