package org.school.stylish.service;

import org.school.stylish.dto.product.ProductListDTO;
import org.school.stylish.dto.product.ProductRequest;
import org.school.stylish.dto.product.ProductSearchDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


public interface ProductService {

    void saveProduct(ProductRequest productRequest);

    ResponseEntity<Map<String, Object>> getAllProducts(Integer paging);

    ResponseEntity<?> searchProducts(String keyword, Integer paging);

    ResponseEntity<?> getProductDetails(Long productId);

    List<ProductListDTO> getProductsByCategory(String category, int page);

    List<ProductListDTO> getAllProducts(int page);

    List<ProductSearchDTO> searchProducts(String keyword, int page);

    ProductListDTO getProductById(Long productId);

    Integer getNextPage(String category, int page);

    ResponseEntity<?> getProductsByCategory(String category, Integer paging);
}
