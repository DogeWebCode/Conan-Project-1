package org.school.stylish.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.ProductDao;
import org.school.stylish.dto.product.*;
import org.school.stylish.exception.NotFoundException;
import org.school.stylish.model.Image;
import org.school.stylish.model.Variant;
import org.school.stylish.service.ProductService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ObjectMapper objectMapper;

    public static final int PAGE_SIZE = 6;
    public static final Set<String> PREDEFINED_CATEGORIES = new HashSet<>(Arrays.asList("men", "women", "accessories"));

    @Override
    public ResponseEntity<?> getProductsByCategory(String category, Integer paging) {

        if (!isCategoryValid(category)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid category");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        int page = (paging == null) ? 0 : paging;
        List<ProductListDTO> products = getProductsByCategory(category, page);

        Map<String, Object> response = new HashMap<>();
        if (products == null || products.isEmpty()) {
            response.put("message", "No products found in this category");
        } else {
            response.put("data", products);
            Integer nextPaging = getNextPage(category, page);
            if (nextPaging != null) {
                response.put("next_paging", nextPaging);
            }
        }
        log.info("Products retrieved successfully for category: {}", category);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllProducts(Integer paging) {
        int page = (paging == null) ? 0 : paging;

        List<ProductListDTO> products = getAllProducts(page);

        if (products == null || products.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "No products found");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Integer nextPaging = (products.size() < ProductServiceImpl.PAGE_SIZE) ? null : page + 1;

        Map<String, Object> response = new HashMap<>();
        response.put("data", products);
        if (nextPaging != null) {
            response.put("next_paging", nextPaging);
        }

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> searchProducts(String keyword, Integer paging) {
        int page = (paging == null) ? 0 : paging;
        List<ProductSearchDTO> products = searchProducts(keyword, page);

        if (products == null || products.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "No products found");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Integer nextPaging = (products.size() < ProductServiceImpl.PAGE_SIZE) ? null : page + 1;
        Map<String, Object> response = new HashMap<>();
        response.put("data", products);
        if (nextPaging != null) {
            response.put("next_paging", nextPaging);
        }
        log.info("Products retrieved successfully for keyword: {}", keyword);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> getProductDetails(Long productId) {
        try {
            ProductListDTO product = getProductById(productId);
            Map<String, Object> response = new HashMap<>();
            response.put("data", product);
            return ResponseEntity.ok().body(response);
        } catch (NotFoundException e) {
            log.warn("Product not found: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An internal server error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @Override
    public List<ProductListDTO> getProductsByCategory(String category, int page) {
        int offset = page * PAGE_SIZE;
        List<ProductListDTO> products = productDao.findProductsByCategory(category, offset, PAGE_SIZE);

        for (ProductListDTO product : products) {
            enrichProductWithList(product);
        }
        return products;
    }

    @Override
    public List<ProductListDTO> getAllProducts(int page) {
        int offset = page * PAGE_SIZE;
        List<ProductListDTO> products = productDao.findAllProducts(offset, PAGE_SIZE);

        for (ProductListDTO product : products) {
            enrichProductWithList(product);
        }
        return products;
    }

    @Override
    public List<ProductSearchDTO> searchProducts(String title, int page) {
        int offset = page * PAGE_SIZE;
        List<ProductSearchDTO> products = productDao.findProductsByTitle(title, offset, PAGE_SIZE);
        for (ProductSearchDTO product : products) {
            enrichProductWithSearch(product);
        }
        return products;
    }

    @Override
    public ProductListDTO getProductById(Long productId) {
        try {
            ProductListDTO product = productDao.findProductById(productId);
            enrichProductWithList(product);
            return product;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Product not found with id: " + productId, e);
        }
    }

    @Override
    public Integer getNextPage(String category, int page) {
        int nextOffset = (page + 1) * PAGE_SIZE;
        Integer count = productDao.countProductsByCategory(category);
        return (count > nextOffset) ? page + 1 : null;
    }

    @Transactional
    @Override
    public void saveProduct(ProductRequest productRequest) {
        Long productId = productDao.insertProduct(productRequest);
        log.info("Inserted product with ID: {}", productId);
        List<Variant> variants = convertJsonToVariants(productRequest.getVariantsJson());
        log.info("Converted JSON to variants: {}", variants);

        for (Variant variant : variants) {
            variant.setProductId(productId);
            String colorName = variant.getColorName();
            String colorCode = variant.getColorCode();
            productDao.insertColor(colorName, colorCode);

            productDao.insertVariant(productId, variant);
            log.info("Inserted variant: {}", variant);
        }
        MultipartFile mainImage = productRequest.getMainImage();

        if (mainImage != null && !mainImage.getOriginalFilename().isEmpty()) {
            String mainImageUrl = productDao.saveFile(mainImage);
            log.info("Received URL from saveFile: {}", mainImageUrl);

            Image mainImageObj = new Image(productId, mainImageUrl, mainImage);
            log.info("URL in Image object: {}", mainImageObj.getImageUrl());

            productDao.insertImage(mainImageObj);
            log.info("Inserted image into database. Image URL: {}", mainImageObj.getImageUrl());

            productDao.insertProductImage(productId, mainImageUrl);
            log.info("Inserted product-image relation. ProductId: {}, ImageUrl: {}", productId, mainImageUrl);
        }

        if (productRequest.getOtherImages() != null) {
            for (MultipartFile image : productRequest.getOtherImages()) {
                if (image != null && !image.getOriginalFilename().isEmpty()) {
                    String imageUrl = productDao.saveFile(image);
                    Image imageObj = new Image(imageUrl);
                    productDao.insertImage(imageObj);
                    productDao.insertProductImage(productId, imageUrl);
                    log.info("Inserted other image: {}", image.getOriginalFilename());
                }
            }
        }
    }

    private List<Variant> convertJsonToVariants(String variantsJson) {
        try {
            if (variantsJson == null || variantsJson.isEmpty()) {
                return List.of();
            }
            return objectMapper.readValue(variantsJson, new TypeReference<List<Variant>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to variants", e);
        }
    }

    private boolean isCategoryValid(String category) {
        if (PREDEFINED_CATEGORIES.contains(category)) {
            return true;
        }

        Integer count = productDao.countProductsByCategory(category);
        return count != null && count > 0;
    }


    private void enrichProductWithList(ProductListDTO product) {
        Long productId = product.getId();

        List<VariantDTO> variants = productDao.findVariantsByProductId(productId);
        product.setVariants(variants);

        List<ColorDTO> colors = productDao.findColorsByVariants(variants);
        product.setColors(colors);

        List<String> sizes = productDao.findSizesByVariants(productId);
        product.setSizes(sizes);

        List<String> images = productDao.findImagesByProductId(productId);
        product.setOtherImages(images);

        String mainImage = product.getMainImage();
        if (images.contains(mainImage)) {
            images = images.stream()
                    .filter(image -> !image.equals(mainImage))
                    .collect(Collectors.toList());
            product.setOtherImages(images);
        }
    }

    private void enrichProductWithSearch(ProductSearchDTO product) {
        Long productId = product.getId();

        List<VariantDTO> variants = productDao.findVariantsByProductId(productId);
        product.setVariants(variants);

        List<ColorDTO> colors = productDao.findColorsByVariants(variants);
        product.setColors(colors);

        List<String> sizes = productDao.findSizesByVariants(productId);
        product.setSizes(sizes);

        List<String> images = productDao.findImagesByProductId(productId);
        product.setOtherImages(images);

        String mainImage = product.getMainImage();
        if (images.contains(mainImage)) {
            images = images.stream()
                    .filter(image -> !image.equals(mainImage))
                    .collect(Collectors.toList());
            product.setOtherImages(images);
        }
    }
}
