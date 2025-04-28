package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.entity.Popup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PopupRepository extends JpaRepository<Popup, Integer> {

    /**
     * 날짜 조건으로 팝업 목록을 조회합니다.
     * 종료일이 가까운 순으로 정렬됩니다.
     */
    @Query("SELECT p FROM Popup p WHERE " +
            "(:startDate IS NULL OR p.endDate >= :startDate) AND " +
            "(:endDate IS NULL OR p.startDate <= :endDate) " +
            "ORDER BY " +
            "CASE WHEN p.endDate > CURRENT_TIMESTAMP THEN 0 ELSE 1 END, " + // 현재 활성화된 것 우선
            "CASE WHEN p.endDate > CURRENT_TIMESTAMP THEN p.endDate ELSE p.endDate END ASC") // 활성화된 것은 종료일이 빠른 순
    Page<Popup> findAllByConditions(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * 타입별로 팝업을 조회하고 종료일이 가까운 순으로 정렬합니다.
     */
    @Query("SELECT p FROM Popup p WHERE p.type.id = :typeId " +
            "ORDER BY " +
            "CASE WHEN p.endDate > CURRENT_TIMESTAMP THEN 0 ELSE 1 END, " + // 현재 활성화된 것 우선
            "CASE WHEN p.endDate > CURRENT_TIMESTAMP THEN p.endDate ELSE p.endDate END ASC") // 종료일이 가까운 순
    Page<Popup> findByTypeIdOrderByEndDateAsc(@Param("typeId") Short typeId, Pageable pageable);

    /**
     * 타입과 날짜 조건으로 팝업을 조회합니다.
     */
    @Query("SELECT p FROM Popup p WHERE " +
            "p.type.id = :typeId AND " +
            "(:startDate IS NULL OR p.endDate >= :startDate) AND " +
            "(:endDate IS NULL OR p.startDate <= :endDate) " +
            "ORDER BY p.endDate ASC")
    Page<Popup> findByTypeIdAndDateBetween(
            @Param("typeId") Short typeId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * 모든 팝업을 가져오고 종료일이 가까운 순으로 정렬합니다.
     */
    @Query("SELECT p FROM Popup p " +
            "ORDER BY " +
            "CASE WHEN p.endDate > CURRENT_TIMESTAMP THEN 0 ELSE 1 END, " + // 현재 활성화된 것 우선
            "CASE WHEN p.endDate > CURRENT_TIMESTAMP THEN p.endDate ELSE p.endDate END ASC") // 종료일이 가까운 순
    Page<Popup> findAllOrderByEndDate(Pageable pageable);
}