package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.repository.OrderManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
}
