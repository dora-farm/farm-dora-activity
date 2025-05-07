package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderManageRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT COUNT(DISTINCT o.id) FROM OrderOption oo " +
            "JOIN oo.order o " +
            "JOIN oo.option opt " +
            "JOIN opt.sale s " +
            "JOIN s.seller sel " +
            "WHERE sel.user.userId = :userId " +
            "AND (:statusId IS NULL OR o.status.id = :statusId) ")
    Long countOrdersBySeller(@Param("userId") Integer userId,
                             @Param("statusId") Short statusId);

    @Query("SELECT COUNT(r) FROM Review r " +
            "JOIN r.sale s " +
            "JOIN s.seller sel " +
            "WHERE sel.user.userId = :userId ")
    Long countReviewsBySeller(@Param("userId") Integer userId);

    @Query("SELECT COUNT(q) FROM Question q " +
            "JOIN q.sale s " +
            "JOIN s.seller sel " +
            "WHERE sel.user.userId = :userId ")
    Long countQuestionsBySeller(@Param("userId") Integer userId);

    @Query("SELECT u.name, u.phoneNum, o.address " +
            "FROM Order o " +
            "JOIN o.user u " +
            "WHERE o.id = :orderId ")
    List<Object[]> findUserInfoByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT re.createdDate, rt.name, re.content, rf.saveFile " +
            "FROM Refund re " +
            "JOIN re.type rt " +
            "LEFT JOIN RefundFile rf ON rf.refund = re " +
            "JOIN re.order o " +
            "WHERE o.id = :orderId")
    List<Object[]> findRefundInfoByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT rf.saveFile, o.id " +
            "FROM Review r " +
            "JOIN ReviewFile rf ON rf.review.id = r.id " +
            "JOIN r.order o " +
            "WHERE r.id = :reviewId")
    List<Object[]> findReviewInfo(@Param("reviewId") Integer reviewId);

    @Query("SELECT q.answer, q.content FROM Question q WHERE q.id = :questionId")
    List<Object[]> findAnswerById(Integer questionId);
}
