package org.school.stylish.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;
    private String category;
    private String title;
    private String description;
    private Integer price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;
    private String mainImage;

}
