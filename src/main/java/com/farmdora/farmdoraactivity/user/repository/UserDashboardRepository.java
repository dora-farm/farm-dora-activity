package com.farmdora.farmdoraactivity.user.repository;

import com.farmdora.farmdoraactivity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDashboardRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.name, u.phoneNum, u.email " +
            "FROM User u " +
            "WHERE u.id = :userId")
    Optional<User> findUserInfo(@Param("userId") Integer userId);
}
