package org.school.stylish.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorDTO {
    @JsonProperty("code")
    private String colorCode;
    @JsonProperty("name")
    private String colorName;

}
