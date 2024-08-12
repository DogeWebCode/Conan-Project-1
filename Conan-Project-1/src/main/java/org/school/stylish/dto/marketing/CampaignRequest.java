package org.school.stylish.dto.marketing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRequest {
    private Long productId;
    private String story;
    private MultipartFile picture;
}
