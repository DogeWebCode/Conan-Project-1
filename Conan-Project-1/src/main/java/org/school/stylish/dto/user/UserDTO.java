package org.school.stylish.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String provider;
    private String name;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String picture;
    @JsonProperty(value = "access_token",access = JsonProperty.Access.WRITE_ONLY)
    private String accessToken;
    private Set<RoleDTO> roles;
}
