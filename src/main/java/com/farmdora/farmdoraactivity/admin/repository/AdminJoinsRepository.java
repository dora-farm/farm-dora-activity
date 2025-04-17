package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.admin.dto.UserDTO;
import com.farmdora.farmdoraactivity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdminJoinsRepository extends JpaRepository<User, Integer> {

    @Query("SELECT new com.farmdora.farmdoraactivity.admin.dto.UserDTO( " +
            "DATE(u.createdDate), COUNT(u.userId)) " +
            "FROM User u " +
            "WHERE DATE(u.createdDate) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(u.createdDate) ")
    List<UserDTO> findAllJoiners(
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate);
}
