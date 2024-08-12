package org.school.stylish.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String prime;
    private OrderInfo order;

    @Data
    public static class OrderInfo {
        private String shipping;
        private String payment;
        private Integer subtotal;
        private Integer freight;
        private Integer total;
        private RecipientDTO recipient;
        private List<OrderItemDTO> list;
    }
}
