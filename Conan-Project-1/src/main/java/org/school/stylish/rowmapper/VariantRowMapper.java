package org.school.stylish.rowmapper;

import org.school.stylish.dto.product.VariantDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VariantRowMapper implements RowMapper<VariantDTO> {
    @Override
    public VariantDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        VariantDTO variant = new VariantDTO();
        variant.setColorCode(rs.getString("color_code"));
        variant.setSizeName(rs.getString("size"));
        variant.setStock(rs.getInt("stock"));
        return variant;
    }
}
