package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManageRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o.id, s.title, opt.name, o.createdDate, u.name, oo.price, os.name " +
            "FROM Order o " +
            "JOIN o.status")

}
