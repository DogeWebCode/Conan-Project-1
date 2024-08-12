package org.school.stylish.dao.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.school.stylish.dao.ProductDao;
import org.school.stylish.dto.product.*;
import org.school.stylish.exception.InsertException;
import org.school.stylish.model.Image;
import org.school.stylish.model.Variant;
import org.school.stylish.rowmapper.ColorRowMapper;
import org.school.stylish.rowmapper.ProductListRowMapper;
import org.school.stylish.rowmapper.ProductSearchRowMapper;
import org.school.stylish.rowmapper.VariantRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseUrl;

    @Override
    public Long insertProduct(ProductRequest productRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                String InsertProductSql = "INSERT INTO product (category, title, description, price, texture, wash, place, note, story, main_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(InsertProductSql, new String[]{"product_id"});
                ps.setString(1, productRequest.getCategory());
                ps.setString(2, productRequest.getTitle());
                ps.setString(3, productRequest.getDescription());
                ps.setInt(4, productRequest.getPrice());
                ps.setString(5, productRequest.getTexture());
                ps.setString(6, productRequest.getWash());
                ps.setString(7, productRequest.getPlace());
                ps.setString(8, productRequest.getNote());
                ps.setString(9, productRequest.getStory());
                ps.setString(10, saveFile(productRequest.getMainImage()));
                return ps;
            }, keyHolder);
            logger.info("Inserted product: {}", productRequest);
        } catch (DataAccessException e) {
            logger.error("Failed to insert product: {}", e.getMessage());
            throw new InsertException("Failed to insert product", e);
        }
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void insertColor(String colorName, String colorCode) {
        try {
            String InsertColorSql = "INSERT INTO color (color_name, color_code) VALUES (?,?)";
            jdbcTemplate.update(InsertColorSql, colorName, colorCode);
            logger.info("Inserted or updated color: {} with code: {}", colorName, colorCode);
        } catch (DataAccessException e) {
            logger.error("Failed to insert color: {}", e.getMessage());
            throw new InsertException("Failed to insert color", e);
        }
    }

    @Override
    public void insertVariant(Long productId, Variant variant) {
        try {
            String InsertVariantSql = "INSERT INTO variant (product_id, color_code, size, stock) VALUES (?,?,?,?)";
            jdbcTemplate.update(InsertVariantSql, productId, variant.getColorCode(), variant.getSizeId(), variant.getStock());
        } catch (DataAccessException e) {
            logger.error("Failed to insert variant: {}", e.getMessage());
            throw new InsertException("Failed to insert variant", e);
        }
    }

    @Override
    public void insertImage(Image image) {
        try {
            String InsertImageSql = "INSERT INTO image (image_url) VALUES (?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(InsertImageSql, new String[]{"image_id"});
                ps.setString(1, image.getImageUrl());
                return ps;
            }, keyHolder);
            Long imageId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            image.setImageId(imageId);
            logger.info("Inserted image: {}", image);
        } catch (DataAccessException e) {
            logger.error("Failed to insert image: {}", e.getMessage());
            throw new InsertException("Failed to insert image", e);
        }
    }


    @Override
    public void insertProductImage(Long productId, String imageUrl) {
        try {
            String findImageIdSql = "SELECT image_id FROM image WHERE image_url = ?";
            Long imageId = jdbcTemplate.queryForObject(findImageIdSql, new Object[]{imageUrl}, Long.class);

            String checkExistenceSql = "SELECT COUNT(*) FROM product_image WHERE product_id = ? AND image_id = ?";
            Integer count = jdbcTemplate.queryForObject(checkExistenceSql, new Object[]{productId, imageId}, Integer.class);

            if (count != null && count > 0) {
                logger.info("Product image already exists for product ID: {} and image ID: {}", productId, imageId);
            } else {
                String insertProductImageSql = "INSERT INTO product_image (product_id, image_id) VALUES (?, ?)";
                jdbcTemplate.update(insertProductImageSql, productId, imageId);
                logger.info("Inserted product image with product ID: {} and image ID: {}", productId, imageId);
            }
        } catch (DataAccessException e) {
            logger.error("Failed to insert product image: {}", e.getMessage());
            throw new InsertException("Failed to insert product image", e);
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean mkdirResult = dir.mkdirs();
            if (!mkdirResult) {
                logger.error("Failed to create directories: {}", uploadDir);
                throw new InsertException("Failed to create directories: " + uploadDir);
            }
        }

        String filePath = uploadDir + file.getOriginalFilename();
        File destFile = new File(filePath);
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
        } catch (IOException e) {
            logger.error("Failed to save file: {}", e.getMessage());
            logger.error("Upload directory: {}", uploadDir);
            logger.error("Destination file path: {}", filePath);
            throw new InsertException("Failed to save file", e);
        }

        return baseUrl + filePath;
    }

    @Override
    public List<ProductListDTO> findAllProducts(int offset, int pageSize) {
        String productSql = "SELECT * FROM product LIMIT ? OFFSET ?";
        // Map to RowMapper through query
        return jdbcTemplate.query(productSql, new Object[]{pageSize, offset}, new ProductListRowMapper());
    }

    @Override
    public List<ProductSearchDTO> findProductsByTitle(String title, int offset, int pageSize) {
        String productSql = "SELECT * FROM product WHERE title LIKE ? LIMIT ? OFFSET ?";
        // Map to RowMapper through query
        return jdbcTemplate.query(productSql, new Object[]{"%" + title + "%", pageSize, offset}, new ProductSearchRowMapper());
    }

    @Override
    public ProductListDTO findProductById(Long productId) {
        String productSql = "SELECT * FROM product WHERE product_id = ?";
        return jdbcTemplate.queryForObject(productSql, new Object[]{productId}, new ProductListRowMapper());
    }

    @Override
    public List<ProductListDTO> findProductsByCategory(String category, int offset, int pageSize) {
        String productSql = "SELECT * FROM product WHERE category = ? LIMIT ? OFFSET ?";
        // Map to RowMapper through query
        return jdbcTemplate.query(productSql, new Object[]{category, pageSize, offset}, new ProductListRowMapper());
    }

    @Override
    public List<VariantDTO> findVariantsByProductId(Long productId) {
        String variantSql = "SELECT * FROM variant WHERE product_id = ?";
        // Map to RowMapper through query
        return jdbcTemplate.query(variantSql, new Object[]{productId}, new VariantRowMapper());
    }

    @Override
    public List<ColorDTO> findColorsByVariants(List<VariantDTO> variants) {
        return variants.stream()
                .map(variant -> {
                    String colorSql = "SELECT * FROM color WHERE color_code = ?";
                    List<ColorDTO> colorList = jdbcTemplate.query(colorSql, new Object[]{variant.getColorCode()}, new ColorRowMapper());
                    return colorList.isEmpty() ? null : colorList.get(0);
                })
                .filter(color -> color != null)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findSizesByVariants(Long productId) {
        String sizeSql = "SELECT DISTINCT size FROM variant WHERE product_id = ?";
        return jdbcTemplate.query(sizeSql, new Object[]{productId}, (rs, rowNum) -> rs.getString("size"));
    }

    @Override
    public List<String> findImagesByProductId(Long productId) {
        String imageSql = "SELECT i.image_url FROM image i JOIN product_image pi ON i.image_id = pi.image_id WHERE pi.product_id = ?";
        return jdbcTemplate.query(imageSql, new Object[]{productId}, (rs, i) -> rs.getString("image_url"));
    }

    @Override
    public Integer countProductsByCategory(String category) {
        String countSql = "SELECT COUNT(*) FROM product WHERE category = ?";
        return jdbcTemplate.queryForObject(countSql, new Object[]{category}, Integer.class);
    }
}