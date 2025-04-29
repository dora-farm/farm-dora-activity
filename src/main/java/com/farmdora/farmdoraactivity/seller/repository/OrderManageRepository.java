package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManageRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT COUNT(DISTINCT o.id) FROM OrderOption oo " +
            "JOIN oo.order o " +
            "JOIN oo.option opt " +
            "JOIN opt.sale s " +
            "WHERE s.seller.id = :sellerId " +
            "AND (:statusId IS NULL OR o.status.id = :statusId) ")
    Long countOrdersBySeller(@Param("sellerId") Integer sellerId,
                             @Param("statusId") Short statusId);

    @Query("SELECT COUNT(r) FROM Review r " +
            "JOIN r.sale s " +
            "WHERE s.seller.id = :sellerId")
    Long countReviewsBySeller(@Param("sellerId") Integer sellerId);

    @Query("SELECT COUNT(q) FROM Question q " +
            "JOIN q.sale s " +
            "WHERE s.seller.id = :sellerId")
    Long countQuestionsBySeller(@Param("sellerId") Integer sellerId);

}
