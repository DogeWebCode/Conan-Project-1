package org.school.stylish.dto.order.tappay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TapPayRequestDTO {
    private String prime;
    @JsonProperty("partner_key")
    private String partnerKey;
    @JsonProperty("merchant_id")
    private String merchantId;
    private String details;
    private Integer amount;
    @JsonProperty("order_number")
    private String orderNumber;
    private TapPayCardholderDTO cardholder;
}
