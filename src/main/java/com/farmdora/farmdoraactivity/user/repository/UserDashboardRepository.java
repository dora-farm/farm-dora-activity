package com.farmdora.farmdoraactivity.user.repository;

import com.farmdora.farmdoraactivity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDashboardRepository extends JpaRepository<User, Integer> {

    @Query("SELECT SUM(oo.price) FROM OrderOption oo JOIN oo.order o WHERE o.user.userId = :userId")
    Long sumTotalAmount(@Param("userId") Integer userId);

    @Query("SELECT COUNT(r.id) FROM Review r JOIN r.order o WHERE o.user.userId = :userId")
    Long countReviewsByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.user.userId = :userId")
    Long countInquiriesByUserId(@Param("userId") Integer userId);
}
