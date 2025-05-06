package com.farmdora.farmdoraactivity.user.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.user.dto.OrderStatusDTO;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO;
import com.farmdora.farmdoraactivity.user.dto.LikePreviewDTO;
import com.farmdora.farmdoraactivity.user.service.UserDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.*;

@RestController
@RequestMapping("api/my/user/dashboard")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class UserDashboardController {

    private final UserDashboardService userDashboardService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());

        UserDashboardDTO userDashboardDTO = userDashboardService.getDashboardInfo(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_USERDASHBOARDINFO_SUCCESS.getMessage(), userDashboardDTO));
    }

    @GetMapping("/order-status")
    public ResponseEntity<?> getOrderStatusInfo(Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());

        List<OrderStatusDTO> orderStatusCounts = userDashboardService.getOrderStatusByUserId(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_ORDERSTATUSINFO_SUCCESS.getMessage(), orderStatusCounts));
    }

    @GetMapping("/likepreview")
    public ResponseEntity<?> getWishPreviewInfo(Principal principal) {
        Integer userId = Integer.parseInt(principal.getName());

        List<LikePreviewDTO> likePreviews = userDashboardService.getWishPreviewByUserId(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_LIKE_SUCCESS.getMessage(), likePreviews));
    }
}