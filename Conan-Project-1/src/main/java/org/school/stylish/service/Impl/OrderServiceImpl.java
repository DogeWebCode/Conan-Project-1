package org.school.stylish.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.OrderDao;

import org.school.stylish.dto.order.OrderDTO;
import org.school.stylish.dto.order.OrderItemDTO;
import org.school.stylish.dto.order.tappay.TapPayResponseDTO;
import org.school.stylish.dto.other.ApiResponse;
import org.school.stylish.exception.NotFoundException;
import org.school.stylish.exception.PaymentFailedException;
import org.school.stylish.model.Order;
import org.school.stylish.service.OrderService;
import org.school.stylish.service.TapPayService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final TapPayService tapPayService;

    @Override
    public String createOrder(OrderDTO orderDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long userId = (Long) authentication.getCredentials();
            String orderNumber = generateOrderNumber();
            Order order = convertToOrder(orderDTO);
            order.setUserId(userId);
            order.setOrderNumber(orderNumber);


            for (OrderItemDTO item : orderDTO.getOrder().getList()) {
                if (!orderDao.productExists(item.getId())) {
                    throw new NotFoundException("Product not found with id: " + item.getId());
                }
            }

            Long orderId = orderDao.insertOrder(order);

            orderDao.insertRecipient(orderId, orderDTO.getOrder().getRecipient());

            for (OrderItemDTO orderItemDTO : orderDTO.getOrder().getList()) {
                orderItemDTO.setOrderId(orderId);
                orderDao.insertOrderItem(orderItemDTO);
            }

            TapPayResponseDTO response = tapPayService.processPayment(orderDTO, orderNumber);
            log.info(response);

            if (response.getStatus() == 0) {
                updateOrderStatus(orderId, "paid");
            } else {
                updateOrderStatus(orderId, "failed");
                log.error("Payment failed with status {}: {}", response.getStatus(), response.getMessage());
                throw new PaymentFailedException("Payment failed");
            }
            return orderNumber;
        } catch (CannotCreateTransactionException e) {
            log.error("Database connection error: {}", e.getMessage());
            throw new CannotCreateTransactionException("Database connection error", e);
        }
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        orderDao.updateOrderStatus(orderId, status);
    }

    private Order convertToOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setShipping(orderDTO.getOrder().getShipping());
        order.setPayment(orderDTO.getOrder().getPayment());
        order.setSubtotal(orderDTO.getOrder().getSubtotal());
        order.setFreight(orderDTO.getOrder().getFreight());
        order.setTotal(orderDTO.getOrder().getTotal());
        order.setPrime(orderDTO.getPrime());
        order.setStatus("unpaid");
        return order;
    }

    @Override
    public ApiResponse<Map<String, String>> createSuccessResponse(String orderNumber) {
        Map<String, String> data = new HashMap<>();
        data.put("number", orderNumber);
        return new ApiResponse<>(data);
    }

    @Override
    public String generateOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(System.currentTimeMillis());
        int random = new Random().nextInt(50000) + 10000;
        return timestamp + random;
    }
}
