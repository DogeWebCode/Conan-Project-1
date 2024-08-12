package org.school.stylish.dto.user.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacebookSignInDTO {
    @NotBlank(message = "Provider cannot be blank")
    private String provider;

    @NotBlank(message = "Access token cannot be blank")
    private String accessToken;

}
