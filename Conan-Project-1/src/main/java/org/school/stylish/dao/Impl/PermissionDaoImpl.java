package org.school.stylish.dao.Impl;

import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.PermissionDao;
import org.school.stylish.dto.user.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Log4j2
@Repository
public class PermissionDaoImpl implements PermissionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PermissionDTO findByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT permission_id, name, description FROM permission WHERE name = ?", new Object[]{name}, (rs, rowNum) -> PermissionDTO.builder().permissionId(rs.getLong("permission_id")).name(rs.getString("name")).description(rs.getString("description")).build());
        } catch (EmptyResultDataAccessException e) {
            log.warn("No permission found with name: {}", name);
            return null;
        }
    }

    @Override
    public PermissionDTO findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT permission_id, name, description FROM permission WHERE permission_id = ?", new Object[]{id}, (rs, rowNum) -> PermissionDTO.builder().permissionId(rs.getLong("permission_id")).name(rs.getString("name")).description(rs.getString("description")).build());
        } catch (EmptyResultDataAccessException e) {
            log.warn("No permission found with id: {}", id);
            return null;
        }
    }

    @Override
    public void save(PermissionDTO permission) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO permission (name, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, permission.getName());
            ps.setString(2, permission.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            permission.setPermissionId(key.longValue());
        }
        log.info("Permission saved with ID: {} and name: {}", permission.getPermissionId(), permission.getName());
    }
}
