package com.farmdora.farmdoraactivity.user.repository;

import com.farmdora.farmdoraactivity.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWishlistRepository extends JpaRepository<Like, Integer> {

    @Query("SELECT l.id, o.id, s.id, s.title, o.name, o.price, sf.saveFile, " +
            "ROUND(AVG(re.score), 1), COUNT(DISTINCT re.id) " +
            "FROM Like l " +
            "JOIN l.sale s ON l.sale = s " +
            "JOIN Option o ON o.sale = s " +
            "JOIN SaleFile sf ON sf.sale = s AND sf.isMain = false " +
            "LEFT JOIN Review re ON re.sale = s " +
            "WHERE l.user.userId = :userId " +
            "AND o.id = (SELECT MIN(o2.id) FROM Option o2 WHERE o2.sale = s) " +
            "GROUP BY s.id, s.title, o.name, o.price, sf.saveFile, o.id, l.id    " +
            "ORDER BY l.id DESC")
    List<Object[]> findWishlistByUserId(@Param("userId") Integer userId);
}
