package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.admin.dto.ReviewDTO;
import com.farmdora.farmdoraactivity.common.response.PageResponseDto;
import com.farmdora.farmdoraactivity.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewManagementRepository extends JpaRepository<Review, Integer> {

    @EntityGraph(attributePaths = {"sale", "order.user"}) // fetch join (JPQL)
    Page<Review> findAll(Pageable pageable);

    Optional<Review> findById(Integer reviewId);

    // 상품명으로 검색
    @Query("SELECT r FROM Review r JOIN r.sale s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Review> findByProductNameContaining(String keyword, Pageable pageable);

    // 작성자명으로 검색
    @Query("SELECT r FROM Review r JOIN r.order o JOIN o.user u WHERE u.name LIKE CONCAT('%', :keyword, '%')")
    Page<Review> findByUserNameContaining(String keyword, Pageable pageable);
}
