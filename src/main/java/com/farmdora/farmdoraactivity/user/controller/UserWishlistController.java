package com.farmdora.farmdoraactivity.user.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.user.dto.WishlistDTO;
import com.farmdora.farmdoraactivity.user.service.UserDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.SEARCH_WISHLIST_SUCCESS;

@RestController
@RequestMapping("api/my/user/wishlist")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class UserWishlistController {

    private final UserDashboardService userDashboardService;

    @GetMapping("/list")
    public ResponseEntity<?> getWishlist(@RequestParam Integer userId) {

        List<WishlistDTO> wishlists = userDashboardService.getWishlistByUserId(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_WISHLIST_SUCCESS.getMessage(), wishlists));
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<?> deleteWishlist(@RequestParam Integer userId) {
//
//
//    }
}
