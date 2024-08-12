package org.school.stylish.service;

import org.school.stylish.dto.order.OrderDTO;
import org.school.stylish.dto.other.ApiResponse;

import java.util.Map;

public interface OrderService {
    String createOrder(OrderDTO orderDTO);

    void updateOrderStatus(Long orderId, String status);

    ApiResponse<Map<String, String>> createSuccessResponse(String orderNumber);

    String generateOrderNumber();
}
