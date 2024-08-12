package org.school.stylish.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long orderId;
    private Long userId;
    private String orderNumber;
    private String shipping;
    private String payment;
    private Integer subtotal;
    private Integer freight;
    private Integer total;
    private String prime;
    private String status;
}
