package org.school.stylish.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProfileDTO {
    private String provider;
    private String name;
    private String email;
    private String picture;
    private Set<RoleDTO> roles;
}
