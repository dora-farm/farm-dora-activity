package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyReviewRepository extends JpaRepository<Review, Integer> {
}
