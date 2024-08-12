package org.school.stylish.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.school.stylish.dto.product.ColorDTO;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private String name;
    private BigDecimal price;
    private ColorDTO color;
    private String size;
    @JsonProperty("qty")
    private Integer quantity;
    private Long orderId;
}
