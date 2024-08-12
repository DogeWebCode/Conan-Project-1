package org.school.stylish.dao.Impl;

import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.RoleDao;
import org.school.stylish.dto.user.PermissionDTO;
import org.school.stylish.dto.user.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

    @Log4j2
    @Repository
    public class RoleDaoImpl implements RoleDao {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public RoleDTO findByName(String name) {
            try {
                return jdbcTemplate.queryForObject(
                        "SELECT role_id, name, description FROM role WHERE name = ?",
                        new Object[]{name},
                        (rs, rowNum) -> RoleDTO.builder()
                                .roleId(rs.getLong("role_id"))
                                .name(rs.getString("name"))
                                .description(rs.getString("description"))
                                .build()
                );
            } catch (EmptyResultDataAccessException e) {
                log.warn("No role found with name: {}", name);
                return null;
            }
        }

        @Override
        public RoleDTO findById(Long id) {
            try {
                return jdbcTemplate.queryForObject(
                        "SELECT role_id, name, description FROM role WHERE role_id = ?",
                        new Object[]{id},
                        (rs, rowNum) -> RoleDTO.builder()
                                .roleId(rs.getLong("role_id"))
                                .name(rs.getString("name"))
                                .description(rs.getString("description"))
                                .build()
                );
            } catch (EmptyResultDataAccessException e) {
                log.warn("No role found with id: {}", id);
                return null;
            }
        }

        @Override
        public void save(RoleDTO role) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO role (name, description) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, role.getName());
                ps.setString(2, role.getDescription());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                role.setRoleId(key.longValue());
            }
            log.info("Role saved with ID: {} and name: {}", role.getRoleId(), role.getName());
        }

        @Override
        public void addPermissionToRole(Long roleId, Long permissionId) {
            String sql = "INSERT INTO role_permission (role_id, permission_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, roleId, permissionId);
            log.info("Added permission {} to role {}", permissionId, roleId);
        }

        @Override
        public Set<PermissionDTO> getRolePermissions(Long roleId) {
            String sql = "SELECT p.permission_id, p.name, p.description FROM permission p " +
                    "JOIN role_permission rp ON p.permission_id = rp.permission_id " +
                    "WHERE rp.role_id = ?";

            List<PermissionDTO> permissions = jdbcTemplate.query(sql, new Object[]{roleId}, (rs, rowNum) -> {
                return PermissionDTO.builder()
                        .permissionId(rs.getLong("permission_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build();
            });

            return new HashSet<>(permissions);
        }
    }
