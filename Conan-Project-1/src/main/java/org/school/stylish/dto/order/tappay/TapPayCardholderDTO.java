package org.school.stylish.dto.order.tappay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TapPayCardholderDTO {
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String name;
    private String email;
}
