package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReviewManagementRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAllByCreatedDateBetweenOrderByCreatedDateDesc(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable);

    Optional<Review> findById(Integer reviewId);

}
