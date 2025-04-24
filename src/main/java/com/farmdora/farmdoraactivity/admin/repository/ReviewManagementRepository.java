package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.admin.dto.ReviewDTO;
import com.farmdora.farmdoraactivity.common.response.PageResponseDto;
import com.farmdora.farmdoraactivity.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewManagementRepository extends JpaRepository<Review, Integer> {

    @EntityGraph(attributePaths = {"sale", "order.user"}) // fetch join (JPQL)
    Page<Review> findAll(Pageable pageable);

    Optional<Review> findById(Integer reviewId);

}
