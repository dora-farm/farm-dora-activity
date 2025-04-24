package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.entity.ReviewFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewFileRepository extends JpaRepository<ReviewFile, Integer> {

    List<ReviewFile> findByReviewId(Integer reviewId);

    void deleteByReviewId(Integer reviewId);
}