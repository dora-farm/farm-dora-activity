package com.farmdora.farmdoraactivity.user.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.user.dto.UserDashboardDTO.*;
import com.farmdora.farmdoraactivity.user.service.UserDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_USERINFO_SUCCESS;

@RestController
@RequestMapping("api/my/user/dashboard")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class UserDashboardController {

    private final UserDashboardService userDashboardService;

    @GetMapping("/info")
    public ResponseEntity<?> findUserInfo(@RequestParam Integer userId) {
        UserInfoDTO userInfoDTO = userDashboardService.findUserInfo(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_USERINFO_SUCCESS.getMessage(), userInfoDTO));
    }
}