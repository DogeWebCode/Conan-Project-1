package org.school.stylish.dto.user.facebook;

import lombok.Data;

@Data
public class FacebookUserDTO {
    private String id;
    private String provider;
    private String name;
    private String email;
    private FacebookPicture picture;

}
