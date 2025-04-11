package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.OrderOption;
import com.farmdora.farmdoraactivity.seller.dto.DashboardDTO.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<OrderOption, Integer> {

    @Query("SELECT DATE(o.createdDate) as createdDate, SUM(oo.price) as price " +
            "FROM OrderOption oo " +
            "JOIN FETCH oo.order o " +
            "JOIN FETCH oo.options opt " +
            "JOIN FETCH opt.sale s " +
            "JOIN FETCH s.seller sel " +
            "WHERE sel.id = :sellerId " +
            "AND o.createdDate BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(o.createdDate) " +
            "ORDER BY o.createdDate")
    List<SalesOverviewDTO> findDailySalesBySellerId (
            @Param("sellerId") Integer sellerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
