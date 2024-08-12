package org.school.stylish.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"color_code", "size", "stock"})
public class VariantDTO {
    @JsonProperty("color_code")
    private String colorCode;
    @JsonProperty("size")
    private String sizeName;
    private Integer stock;
}
