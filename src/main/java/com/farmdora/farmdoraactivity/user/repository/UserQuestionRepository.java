package com.farmdora.farmdoraactivity.user.repository;

import com.farmdora.farmdoraactivity.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserQuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByUser_UserIdOrderByCreatedDateDesc(Integer userId);

    @Query("SELECT q " +
            "FROM Question q " +
            "WHERE q.user.userId = :userId AND " +
            "q.createdDate >= :startDate AND q.createdDate <= :endDate " +
            "ORDER BY q.createdDate DESC ")
    List<Question> findQuestionByPeriod(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


}
