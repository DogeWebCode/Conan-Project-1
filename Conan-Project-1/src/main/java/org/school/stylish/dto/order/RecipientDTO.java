package org.school.stylish.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipientDTO {
    private Long orderId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String time;
}
