package org.school.stylish.dao.Impl;

import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.UserDao;
import org.school.stylish.dto.user.RoleDTO;
import org.school.stylish.dto.user.SignUpDTO;
import org.school.stylish.dto.user.UserDTO;
import org.school.stylish.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDTO InsertUser(SignUpDTO signUpDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user (provider, name, email, password, picture) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, signUpDTO.getProvider());
            ps.setString(2, signUpDTO.getName());
            ps.setString(3, signUpDTO.getEmail());
            ps.setString(4, signUpDTO.getPassword());
            ps.setString(5, signUpDTO.getPicture());
            return ps;
        }, keyHolder);

        // Get the automatically generated ID
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("Failed to retrieve auto-generated key");
        }
        long generatedId = key.longValue();

        UserDTO newUser = UserDTO.builder()
                .id(generatedId)
                .provider(signUpDTO.getProvider())
                .name(signUpDTO.getName())
                .email(signUpDTO.getEmail())
                .picture(signUpDTO.getPicture())
                .build();

        log.info("User saved with ID: {} and email: {}", newUser.getId(), newUser.getEmail());
        return newUser;

    }

    @Override
    public UserDTO findUserByEmail(String email) {
        try {
            UserDTO user = jdbcTemplate.queryForObject(
                    "SELECT user_id, provider, name, email, password, picture FROM user WHERE email = ?",
                    new Object[]{email},
                    new UserRowMapper()
            );
            if (user != null) {
                user.setRoles(getUserRoles(user.getId()));
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            log.warn("No user found with email: {}", email);
            return null;
        }
    }

    @Override
    public Set<RoleDTO> getUserRoles(Long userId) {
        String sql = "SELECT r.role_id, r.name, r.description FROM user_role ur " +
                "JOIN role r ON ur.role_id = r.role_id " +
                "WHERE ur.user_id = ?";
        List<RoleDTO> roles = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> RoleDTO.builder()
                .roleId(rs.getLong("role_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build());
        return new HashSet<>(roles);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        String sql = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
        log.info("Role with ID: {} added to user with ID: {}", roleId, userId);
    }
}
