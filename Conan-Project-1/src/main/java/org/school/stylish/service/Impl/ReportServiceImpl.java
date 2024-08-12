package org.school.stylish.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.Impl.ReportDaoImpl;
import org.school.stylish.dto.report.PaymentsDTO;
import org.school.stylish.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReportServiceImpl implements ReportService {

    private final ReportDaoImpl reportDao;
    private final RedisTemplate redisTemplate;

    @Override
    public List<PaymentsDTO> getAllOrders() {
        List<Map<String, Object>> rows = reportDao.findAllOrders();
        int totalOrders = reportDao.countTotalOrders();
        System.out.println("Total Orders: " + totalOrders);

        Map<Long, Integer> userPayment = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long userId = (Long) row.get("user_id");
            Integer total = (Integer) row.get("total");

            userPayment.put(userId, userPayment.getOrDefault(userId, 0) + total);
        }
        List<PaymentsDTO> payments = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : userPayment.entrySet()) {
            PaymentsDTO paymentsDTO = new PaymentsDTO();
            paymentsDTO.setUserId(entry.getKey());
            paymentsDTO.setTotalPayment(entry.getValue());
            payments.add(paymentsDTO);
        }
        return payments;
    }

    public void submitReportJob() throws JsonProcessingException {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("work_type", "1");
        taskMap.put("email","james56012623@gmail.com");
        taskMap.put("create_time", System.currentTimeMillis());
        redisTemplate.opsForList().leftPush("report", taskMap);
        log.info("Task submitted to Redis: " + taskMap);
    }
}
