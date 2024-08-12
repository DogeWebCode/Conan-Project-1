package org.school.stylish.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private MultipartFile multipartFile;

    public Image(Long productId, String mainImageUrl, MultipartFile mainImage) {
        this.productId = productId;
        this.imageUrl = mainImageUrl;
        this.multipartFile = mainImage;

    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
