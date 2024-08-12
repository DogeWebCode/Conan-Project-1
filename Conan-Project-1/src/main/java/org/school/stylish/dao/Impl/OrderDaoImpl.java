package org.school.stylish.dao.Impl;

import lombok.RequiredArgsConstructor;
import org.school.stylish.dao.OrderDao;
import org.school.stylish.dto.order.OrderItemDTO;
import org.school.stylish.dto.order.RecipientDTO;
import org.school.stylish.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long insertOrder(Order order) {
        String sql = "INSERT INTO `order` (user_id, order_number,shipping, payment, subtotal, freight, total, prime, status) VALUES(?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, order.getUserId());
            ps.setString(2,order.getOrderNumber());
            ps.setString(3, order.getShipping());
            ps.setString(4, order.getPayment());
            ps.setInt(5, order.getSubtotal());
            ps.setInt(6, order.getFreight());
            ps.setInt(7, order.getTotal());
            ps.setString(8, order.getPrime());
            ps.setString(9, order.getStatus());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }


    @Override
    public void insertRecipient(Long orderId, RecipientDTO recipientDTO) {
        String sql = "INSERT INTO recipient (order_id, name, phone, email, address, time) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                orderId,
                recipientDTO.getName(),
                recipientDTO.getPhone(),
                recipientDTO.getEmail(),
                recipientDTO.getAddress(),
                recipientDTO.getTime()
        );
    }

    @Override
    public void insertOrderItem(OrderItemDTO orderItemDTO) {
        String sql = "INSERT INTO order_item (order_id, product_id, name, price, color_name, color_code, size, quantity) VALUES(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                orderItemDTO.getOrderId(),
                orderItemDTO.getId(),
                orderItemDTO.getName(),
                orderItemDTO.getPrice(),
                orderItemDTO.getColor().getColorName(),
                orderItemDTO.getColor().getColorCode(),
                orderItemDTO.getSize(),
                orderItemDTO.getQuantity()
        );
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        String sql = "UPDATE `order` SET status = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, status, orderId);
    }

    @Override
    public boolean productExists(Long productId) {
        String sql = "SELECT COUNT(*) FROM product WHERE product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, productId);
        return count != null && count > 0;
    }
}
