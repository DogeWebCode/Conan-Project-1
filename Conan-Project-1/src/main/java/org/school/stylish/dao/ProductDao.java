package org.school.stylish.dao;

import org.school.stylish.dto.product.*;
import org.school.stylish.model.Image;
import org.school.stylish.model.Variant;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductDao {
    Long insertProduct(ProductRequest productRequest);

    void insertColor(String colorName, String colorCode);

    void insertVariant(Long productId, Variant variant);

    void insertImage(Image image);

    void insertProductImage(Long productId, String imageUrl);

    String saveFile(MultipartFile image);

    List<ProductListDTO> findProductsByCategory(String category, int offset, int pageSize);

    List<VariantDTO> findVariantsByProductId(Long productId);

    List<ColorDTO> findColorsByVariants(List<VariantDTO> variants);

    List<String> findSizesByVariants(Long productId);

    List<String> findImagesByProductId(Long productId);

    Integer countProductsByCategory(String category);

    List<ProductListDTO> findAllProducts(int offset, int pageSize);

    List<ProductSearchDTO> findProductsByTitle(String title, int offset, int pageSize);

    ProductListDTO findProductById(Long productId);
}
