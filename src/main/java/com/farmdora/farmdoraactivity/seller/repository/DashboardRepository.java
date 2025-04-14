package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<OrderOption, Integer> {

    @Query("SELECT DATE(o.createdDate), SUM(oo.price), sel.id " +
            "FROM OrderOption oo " +
            "JOIN oo.order o " +
            "JOIN oo.options opt " +
            "JOIN opt.sale s " +
            "JOIN s.seller sel " +
            "WHERE sel.id = :sellerId AND o.createdDate " +
            "BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(o.createdDate), sel.id ")
    List<Object[]> findDailySalesBySellerId (
            @Param("sellerId") Integer sellerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
