package com.farmdora.farmdoraactivity.user.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.user.dto.OrderStatusDTO;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO;
import com.farmdora.farmdoraactivity.user.dto.WishPreviewDTO;
import com.farmdora.farmdoraactivity.user.dto.WishlistDTO;
import com.farmdora.farmdoraactivity.user.service.UserDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.*;

@RestController
@RequestMapping("api/my/user/dashboard")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class UserDashboardController {

    private final UserDashboardService userDashboardService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam Integer userId) {

        UserDashboardDTO userDashboardDTO = userDashboardService.getDashboardInfo(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_USERDASHBOARDINFO_SUCCESS.getMessage(), userDashboardDTO));
    }

    @GetMapping("/order-status")
    public ResponseEntity<?> getOrderStatusInfo(@RequestParam Integer userId) {

        List<OrderStatusDTO> orderStatusCounts = userDashboardService.getOrderStatusByUserId(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_ORDERSTATUSINFO_SUCCESS.getMessage(), orderStatusCounts));
    }

    @GetMapping("/wishpreview")
    public ResponseEntity<?> getWishPreviewInfo(@RequestParam Integer userId) {

        List<WishPreviewDTO> wishPreviews = userDashboardService.getWishPreviewByUserId(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_WISHLIST_SUCCESS.getMessage(), wishPreviews));
    }
}