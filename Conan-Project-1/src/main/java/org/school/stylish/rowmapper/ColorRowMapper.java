package org.school.stylish.rowmapper;

import org.school.stylish.dto.product.ColorDTO;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorRowMapper implements RowMapper<ColorDTO> {
    @Override
    public ColorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColorDTO color = new ColorDTO();
        color.setColorCode(rs.getString("color_code"));
        color.setColorName(rs.getString("color_name"));
        return color;
    }
}
