package org.school.stylish.rowmapper;

import org.school.stylish.dto.product.ProductListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductListRowMapper implements RowMapper<ProductListDTO> {
    @Override
    public ProductListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductListDTO product = new ProductListDTO();
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
