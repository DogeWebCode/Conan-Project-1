package org.school.stylish.dto.order.tappay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TapPayResponseDTO {
    private Integer status;
    @JsonProperty("msg")
    private String message;
    @JsonProperty("order_number")
    private String orderNumber;
}
