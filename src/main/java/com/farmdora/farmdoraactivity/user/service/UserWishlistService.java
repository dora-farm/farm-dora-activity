package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.user.dto.WishlistDTO;

import java.util.List;

public interface UserWishlistService {

    List<WishlistDTO> getWishlistByUserId(Integer userId);

    void deleteSelectedWishlist(List<Integer> likeIds);

}
