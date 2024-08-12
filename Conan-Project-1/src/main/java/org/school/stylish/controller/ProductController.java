package org.school.stylish.controller;

import lombok.RequiredArgsConstructor;
import org.school.stylish.dto.product.ProductRequest;
import org.school.stylish.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/1.0/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category, @RequestParam(value = "paging", required = false) Integer paging) {
        return productService.getProductsByCategory(category, paging);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProducts(@RequestParam(value = "paging", required = false) Integer paging) {
        return productService.getAllProducts(paging);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam("keyword") String keyword, @RequestParam(value = "paging", required = false) Integer paging) {
        return productService.searchProducts(keyword, paging);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getProductDetails(@RequestParam("id") Long productId) {
        return productService.getProductDetails(productId);
    }

    @PostMapping(value = "/addProduct", consumes = "multipart/form-data")
    public ResponseEntity<String> addProduct(
            @RequestParam("category") String category,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") Integer price,
            @RequestParam("texture") String texture,
            @RequestParam("wash") String wash,
            @RequestParam("place") String place,
            @RequestParam("note") String note,
            @RequestParam("story") String story,
            @RequestParam("variants") String variantsJson,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam("otherImages") List<MultipartFile> otherImages) {

        ProductRequest productRequest = new ProductRequest(
                category, title, description, price, texture, wash, place, note, story, variantsJson, mainImage, otherImages);
        productService.saveProduct(productRequest);

        return ResponseEntity.ok().body("{\"message\": \"Product added successfully\"}");
    }
}

