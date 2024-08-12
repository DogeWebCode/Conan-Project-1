package org.school.stylish.dao;

import org.school.stylish.dto.user.RoleDTO;
import org.school.stylish.dto.user.SignUpDTO;
import org.school.stylish.dto.user.UserDTO;

import java.util.Set;

public interface UserDao {
    UserDTO InsertUser(SignUpDTO signUpDTO);

    UserDTO findUserByEmail(String email);

    Set<RoleDTO> getUserRoles(Long userId);

    void addRoleToUser(Long userId, Long roleId);
}
