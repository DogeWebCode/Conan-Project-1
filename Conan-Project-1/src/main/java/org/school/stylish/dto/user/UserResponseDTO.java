package org.school.stylish.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"access_token", "access_expired", "user"})
public class UserResponseDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_expired")
    private long accessExpired;
    private UserDTO user;
}
