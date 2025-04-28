package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.user.dto.LikeDTO;

import java.util.List;

public interface UserLikeService {

    List<LikeDTO> getLikeByUserId(Integer userId);

    void deleteSelectedLike(List<Integer> likeIds);

}
