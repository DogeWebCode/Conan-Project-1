package org.school.stylish.dao;

import org.school.stylish.dto.order.OrderItemDTO;
import org.school.stylish.dto.order.RecipientDTO;
import org.school.stylish.model.Order;

public interface OrderDao {
    Long insertOrder(Order order);

    void insertRecipient(Long orderId, RecipientDTO recipientDTO);

    void insertOrderItem(OrderItemDTO orderItemDTO);

    void updateOrderStatus(Long orderId, String status);


    boolean productExists(Long productId);
}
