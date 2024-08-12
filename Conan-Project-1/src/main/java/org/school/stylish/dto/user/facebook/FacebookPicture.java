package org.school.stylish.dto.user.facebook;

import lombok.Data;

@Data

public class FacebookPicture {
    private PictureData data;

    @Data
    public static class PictureData {
        private String url;
    }
}
