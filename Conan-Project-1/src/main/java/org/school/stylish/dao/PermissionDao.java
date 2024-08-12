package org.school.stylish.dao;

import org.school.stylish.dto.user.PermissionDTO;

public interface PermissionDao {
    PermissionDTO findByName(String name);

    PermissionDTO findById(Long id);

    void save(PermissionDTO permission);
}
