package org.school.stylish.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variant {
    private Long variantId;
    private Long productId;
    private String colorCode;
    private String colorName;
    private String sizeId;
    private Integer stock;
}
