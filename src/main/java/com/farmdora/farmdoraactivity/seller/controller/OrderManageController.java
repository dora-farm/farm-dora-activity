package com.farmdora.farmdoraactivity.seller.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.seller.service.OrderManageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_ORDERMANAGECOUNT_SUCCESS;
import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_ORDERMANAGELIST_SUCCESS;

@RestController
@RequestMapping("/api/my/seller/order")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class OrderManageController {
    private final OrderManageService orderManageService;

    @GetMapping
    public ResponseEntity<HttpResponse> getOrderManageCount(@RequestParam Integer sellerId) {
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_ORDERMANAGECOUNT_SUCCESS.getMessage(),
                        orderManageService.getAllStatistics(sellerId)));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getOrderManageList(@RequestParam Integer sellerId) {
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_ORDERMANAGELIST_SUCCESS.getMessage(),
                        orderManageService.getOrderList(sellerId)));
    }
}
