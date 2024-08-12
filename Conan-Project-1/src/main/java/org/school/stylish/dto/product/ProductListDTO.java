package org.school.stylish.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
    private Long id;
    private String category;
    private String title;
    private String description;
    private Integer price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;
    private List<ColorDTO> colors;
    private List<String> sizes;
    private List<VariantDTO> variants;
    @JsonProperty("main_image")
    private String mainImage;
    @JsonProperty("images")
    private List<String> otherImages;

}
