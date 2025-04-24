package com.farmdora.farmdoraactivity.user.repository;

import com.farmdora.farmdoraactivity.entity.User;
import com.farmdora.farmdoraactivity.user.dto.OrderStatusDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserDashboardRepository extends JpaRepository<User, Integer> {

    @Query("SELECT SUM(oo.price) FROM OrderOption oo JOIN oo.order o WHERE o.user.userId = :userId")
    Long sumTotalAmount(@Param("userId") Integer userId);

    @Query("SELECT COUNT(r.id) FROM Review r JOIN r.order o WHERE o.user.userId = :userId")
    Long countReviewsByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.user.userId = :userId")
    Long countInquiriesByUserId(@Param("userId") Integer userId);

    @Query("SELECT new com.farmdora.farmdoraactivity.user.dto.OrderStatusDTO( " +
            "CAST(CASE WHEN o.status.id IN (5, 6) THEN 5 ELSE o.status.id END AS short), " +
            "COUNT(o.id)) " +
            "FROM Order o " +
            "WHERE o.user.userId = :userId AND o.createdDate BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(CASE WHEN o.status.id IN (5, 6) THEN 5 ELSE o.status.id END AS short) " +
            "ORDER BY CAST(CASE WHEN o.status.id IN (5, 6) THEN 5 ELSE o.status.id END AS short)")
    List<OrderStatusDTO> findOrderStatusByUserId(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s.id, s.title, o.name, o.price, sf.saveFile " +
            "FROM Like l " +
            "JOIN l.sale s " +
            "JOIN Option o ON s.id = o.sale.id AND o.id = (" +
                "SELECT MIN(id) " +
                "FROM Option WHERE sale.id = s.id) " +
            "JOIN SaleFile sf ON s.id = sf.sale.id AND sf.isMain = false " +
            "WHERE l.user.userId = :userId " +
            "ORDER BY l.id DESC ")
    List<Object[]> findWishPreviewByUserId(@Param("userId") Integer userId, Pageable pageable);
}
