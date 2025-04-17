package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.admin.dto.OrderDTO;
import com.farmdora.farmdoraactivity.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdminSalesRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT new com.farmdora.farmdoraactivity.admin.dto.OrderDTO(" +
            "DATE(o.createdDate), SUM(oo.price)) " +
            "FROM OrderOption oo " +
            "JOIN oo.order o " +
            "WHERE DATE(o.createdDate) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(o.createdDate) ")
    List<OrderDTO> findAllSumPrice (
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
