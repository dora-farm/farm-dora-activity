package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.OrderManageDTO;
import com.farmdora.farmdoraactivity.seller.repository.OrderManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderManageService {
    private final OrderManageRepository orderManageRepository;

    public Map<String, Long> getAllStatistics(Integer sellerId) {
        Map<String, Long> statistics = new HashMap<>();

        statistics.put("totalOrders", orderManageRepository.countOrdersBySeller(sellerId, null));
        statistics.put("newOrders", orderManageRepository.countOrdersBySeller(sellerId, (short)1));
        statistics.put("exchangeOrders", orderManageRepository.countOrdersBySeller(sellerId, (short)6));
        statistics.put("refundOrders", orderManageRepository.countOrdersBySeller(sellerId, (short)5));
        statistics.put("cancelOrders", orderManageRepository.countOrdersBySeller(sellerId, (short)4));
        statistics.put("reviewCount", orderManageRepository.countReviewsBySeller(sellerId));
        statistics.put("questionCount", orderManageRepository.countQuestionsBySeller(sellerId));

        return statistics;
    }

    public Map<String, List<OrderManageDTO>> getOrderList(Integer sellerId) {
        Map<String, List<OrderManageDTO>> orderMap = new HashMap<>();

        orderMap.put("all", getOrderListForStatus(sellerId, null));
        orderMap.put("new", getOrderListForStatus(sellerId, (short)1));
        orderMap.put("exchange", getOrderListForStatus(sellerId, (short)6));
        orderMap.put("refund", getOrderListForStatus(sellerId, (short)5));
        orderMap.put("cancel", getOrderListForStatus(sellerId, (short)4));

        return orderMap;
    }

    private List<OrderManageDTO> getOrderListForStatus(Integer sellerId, Short statusId) {
        List<Object[]> orderList = orderManageRepository.findByOrders(sellerId, statusId);
        return orderList.stream()
                .map(OrderManageDTO::from)
                .collect(Collectors.toList());
    }
}
