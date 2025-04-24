package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.user.dto.WishlistDTO;
import com.farmdora.farmdoraactivity.user.repository.UserWishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserWishlistServiceImpl implements UserWishlistService {

    private final UserWishlistRepository userWishlistRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WishlistDTO> getWishlistByUserId(Integer userId) {

        List<Object[]> results = userWishlistRepository.findWishlistByUserId(userId);
        log.info("찜리스트: {}", results);

        return results.stream()
                .map(WishlistDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSelectedWishlist(List<Integer> likeIds) {
        userWishlistRepository.deleteAllById(likeIds);
    }
}
