package com.farmdora.farmdoraactivity.seller.service;

import com.farmdora.farmdoraactivity.seller.dto.OrderDetailDTO;
import com.farmdora.farmdoraactivity.seller.dto.QuestionInfoDTO;
import com.farmdora.farmdoraactivity.seller.dto.RefundInfoDTO;
import com.farmdora.farmdoraactivity.seller.dto.ReviewInfoDTO;
import com.farmdora.farmdoraactivity.seller.repository.OrderManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderManageService {
    private final OrderManageRepository orderManageRepository;

    public Map<String, Long> getAllStatistics(Integer userId) {
        Map<String, Long> statistics = new HashMap<>();

        statistics.put("totalOrders", orderManageRepository.countOrdersBySeller(userId, null));
        statistics.put("newOrders", orderManageRepository.countOrdersBySeller(userId, (short)1));
        statistics.put("exchangeOrders", orderManageRepository.countOrdersBySeller(userId, (short)6));
        statistics.put("refundOrders", orderManageRepository.countOrdersBySeller(userId, (short)5));
        statistics.put("cancelOrders", orderManageRepository.countOrdersBySeller(userId, (short)4));
        statistics.put("reviewCount", orderManageRepository.countReviewsBySeller(userId));
        statistics.put("questionCount", orderManageRepository.countQuestionsBySeller(userId));

        return statistics;
    }

    public OrderDetailDTO getDetailInfo(Integer orderId) {
        List<Object[]> results = orderManageRepository.findUserInfoByOrderId(orderId);

        if (results.isEmpty()) {
            return null;
        }

        return OrderDetailDTO.from(results.get(0));
    }

    public List<RefundInfoDTO> getRefundInfo(Integer orderId) {
        List<Object[]> results = orderManageRepository.findRefundInfoByOrderId(orderId);

        return results.stream()
                .map(RefundInfoDTO::from)
                .collect(Collectors.toList());
    }

    public List<ReviewInfoDTO> getReviewInfo(Integer reviewId) {
        List<Object[]> results = orderManageRepository.findReviewInfo(reviewId);

        return results.stream()
                .map(ReviewInfoDTO::from)
                .collect(Collectors.toList());
    }

    public List<QuestionInfoDTO> getQuestionAnswer(Integer questionId) {
        List<Object[]> results = orderManageRepository.findAnswerById(questionId);

        return results.stream()
                .map(QuestionInfoDTO::from)
                .collect(Collectors.toList());
    }
}
