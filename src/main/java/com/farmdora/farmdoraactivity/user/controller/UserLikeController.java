package com.farmdora.farmdoraactivity.user.controller;

import com.farmdora.farmdoraactivity.common.response.HttpResponse;
import com.farmdora.farmdoraactivity.user.dto.LikeDTO;
import com.farmdora.farmdoraactivity.user.service.UserLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.farmdora.farmdoraactivity.common.response.SuccessMessage.*;

@RestController
@RequestMapping("api/my/user/like")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class UserLikeController {

    private final UserLikeService userLikeService;

    @GetMapping
    public ResponseEntity<?> getLike(Principal principal) {

        Integer userId = Integer.parseInt(principal.getName());

        List<LikeDTO> likes = userLikeService.getLikeByUserId(userId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_LIKE_SUCCESS.getMessage(), likes));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSelectedLike(@RequestBody List<Integer> likeIds) {
        userLikeService.deleteSelectedLike(likeIds);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, SEARCH_DELETELIKE_SUCCESS.getMessage(), null));
    }
}
