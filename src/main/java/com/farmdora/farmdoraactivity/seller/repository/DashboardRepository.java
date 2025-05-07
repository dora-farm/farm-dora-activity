package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.OrderOption;
import com.farmdora.farmdoraactivity.seller.dto.ProductRatioDTO;
import com.farmdora.farmdoraactivity.seller.dto.SalesOverviewDTO;
import com.farmdora.farmdoraactivity.seller.dto.StatusRatioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<OrderOption, Integer> {

    @Query("SELECT new com.farmdora.farmdoraactivity.seller.dto.SalesOverviewDTO(DATE(o.createdDate), SUM(oo.price), sel.id) " +
            "FROM OrderOption oo " +
            "JOIN oo.order o " +
            "JOIN oo.option opt " +
            "JOIN opt.sale s " +
            "JOIN s.seller sel " +
            "WHERE sel.id = :sellerId " +
            "AND DATE(o.createdDate) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(o.createdDate), sel.id " +
            "ORDER BY DATE(o.createdDate) ASC ")
    List<SalesOverviewDTO> findDailySalesBySellerId (
            @Param("sellerId") Integer sellerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(oo) FROM OrderOption oo " +
            "JOIN oo.option o " +
            "JOIN o.sale s " +
            "WHERE s.seller.id = :sellerId ")
    int countBySellerId(@Param("sellerId") Integer sellerId);

    @Query("SELECT new com.farmdora.farmdoraactivity.seller.dto.ProductRatioDTO(st.name, COUNT(oo)) " +
            "FROM OrderOption oo " +
            "JOIN oo.option opt " +
            "JOIN opt.sale s " +
            "JOIN s.type st " +
            "WHERE s.seller.id = :sellerId " +
            "GROUP BY st.id, st.name ")
    List<ProductRatioDTO> findProductTypeCountBySellerId(@Param("sellerId") Integer sellerId);

    @Query("SELECT new com.farmdora.farmdoraactivity.seller.dto.StatusRatioDTO(os.name, COUNT(o.id)) " +
            "FROM OrderOption oo " +
            "JOIN oo.order o " +
            "JOIN o.status os " +
            "JOIN oo.option opt " +
            "JOIN opt.sale s " +
            "WHERE s.seller.id = :sellerId " +
            "AND os.name NOT IN ('배송준비', '배송중')" +
            "GROUP BY os.name ")
    List<StatusRatioDTO> findStatusTypeCountBySellerId(@Param("sellerId") Integer sellerId);
}