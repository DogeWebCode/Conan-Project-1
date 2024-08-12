package org.school.stylish.rowmapper;

import org.school.stylish.dto.product.ProductSearchDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductSearchRowMapper implements RowMapper<ProductSearchDTO> {
    @Override
    public ProductSearchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductSearchDTO product = new ProductSearchDTO();
        product.setId(rs.getLong("product_id"));
        product.setCategory(rs.getString("category"));
        product.setTitle(rs.getString("title"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getInt("price"));
        product.setTexture(rs.getString("texture"));
        product.setWash(rs.getString("wash"));
        product.setPlace(rs.getString("place"));
        product.setNote(rs.getString("note"));
        product.setStory(rs.getString("story"));
        product.setMainImage(rs.getString("main_image"));
        return product;
    }
}
