package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.entity.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderOptionRepository extends JpaRepository<OrderOption, Integer> {

    List<OrderOption> findByOrderId(Integer orderId);
}