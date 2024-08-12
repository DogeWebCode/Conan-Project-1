package org.school.stylish.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsDTO {
    private Long userId;
    private Integer totalPayment;
}
