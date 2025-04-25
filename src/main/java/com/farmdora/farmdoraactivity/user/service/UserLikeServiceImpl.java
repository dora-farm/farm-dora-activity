package com.farmdora.farmdoraactivity.user.service;

import com.farmdora.farmdoraactivity.user.dto.LikeDTO;
import com.farmdora.farmdoraactivity.user.repository.UserLikeRepository;
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
public class UserLikeServiceImpl implements UserLikeService {

    private final UserLikeRepository userLikeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LikeDTO> getLikeByUserId(Integer userId) {

        List<Object[]> results = userLikeRepository.findLikeByUserId(userId);
        log.info("찜리스트: {}", results);

        return results.stream()
                .map(LikeDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSelectedLike(List<Integer> likeIds) {
        userLikeRepository.deleteAllById(likeIds);
    }
}
