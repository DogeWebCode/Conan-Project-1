package org.school.stylish.dto.product;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String category;
    private String title;
    private String description;
    private Integer price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;
    private String variantsJson;
    private MultipartFile mainImage;
    private List<MultipartFile> otherImages;
}
