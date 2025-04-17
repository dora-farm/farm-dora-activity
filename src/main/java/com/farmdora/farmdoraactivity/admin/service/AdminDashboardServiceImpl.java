package com.farmdora.farmdoraactivity.admin.service;

import com.farmdora.farmdoraactivity.admin.dto.OrderDTO;
import com.farmdora.farmdoraactivity.admin.dto.UserDTO;
import com.farmdora.farmdoraactivity.admin.mapper.AdminDashboardMapper;
import com.farmdora.farmdoraactivity.admin.repository.AdminJoinsRepository;
import com.farmdora.farmdoraactivity.admin.repository.AdminSalesRepository;
import com.farmdora.farmdoraactivity.seller.dto.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final AdminJoinsRepository adminJoinsRepository;
    private final AdminSalesRepository adminSalesRepository;
    private final AdminDashboardMapper adminDashboardMapper;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSalesData(LocalDate startDate, LocalDate endDate, Period period) {
        List<OrderDTO> findAllSumPrice = findAllSumPrice(startDate, endDate);
        if (period.equals(Period.DAILY)) {
            return adminDashboardMapper.formatDailyData(findAllSumPrice);
        } else if (period.equals(Period.WEEKLY)) {
            return adminDashboardMapper.formatWeeklyData(findAllSumPrice);
        } else if (period.equals(Period.MONTHLY)) {
            return adminDashboardMapper.formatMonthlyData(findAllSumPrice);
        }
        return adminDashboardMapper.formatDailyData(findAllSumPrice);
    }

    private List<OrderDTO> findAllSumPrice(LocalDate startDate, LocalDate endDate) {
        List<OrderDTO> salesDate = adminSalesRepository.findAllSumPrice(startDate, endDate);

        Map<LocalDate, OrderDTO> salesByDate = salesDate.stream()
                .collect(Collectors.toMap(OrderDTO::getCreatedDate, dto -> dto));

        List<OrderDTO> result = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            OrderDTO dto = salesByDate.get(currentDate);
            if (dto == null) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
                dto = new OrderDTO(sqlDate, 0L);
            }
            result.add(dto);
            currentDate = currentDate.plusDays(1);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUsersData(LocalDate startDate, LocalDate endDate, Period period) {
        List<UserDTO> findAllJoiners = adminJoinsRepository.findAllJoiners(startDate, endDate);
        if (period.equals(Period.DAILY)) {
            return adminDashboardMapper.formatDailyUser(findAllJoiners);
        } else if (period.equals(Period.WEEKLY)) {
            return adminDashboardMapper.formatWeeklyUser(findAllJoiners);
        } else if (period.equals(Period.MONTHLY)) {
            return adminDashboardMapper.formatMonthlyUser(findAllJoiners);
        }
        return adminDashboardMapper.formatDailyUser(findAllJoiners);
    }

    private List<UserDTO> findAllJoiners(LocalDate startDate, LocalDate endDate) {
        List<UserDTO> joinsDate = adminJoinsRepository.findAllJoiners(startDate, endDate);

        Map<LocalDate, UserDTO> joinsByDate = joinsDate.stream()
                .collect(Collectors.toMap(UserDTO::getCreatedDate, dto -> dto));

        List<UserDTO> result = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            UserDTO dto = joinsByDate.get(currentDate);
            if (dto == null) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
                dto = new UserDTO(sqlDate, 0L);
            }
            result.add(dto);
            currentDate = currentDate.plusDays(1);
        }
        return result;
    }
}
