package org.school.stylish.dao;

import org.school.stylish.dto.user.PermissionDTO;
import org.school.stylish.dto.user.RoleDTO;

import java.util.Set;

public interface RoleDao {
    RoleDTO findByName(String name);
    RoleDTO findById(Long id);
    void save(RoleDTO role);
    void addPermissionToRole(Long roleId, Long permissionId);
    Set<PermissionDTO> getRolePermissions(Long roleId);
}
