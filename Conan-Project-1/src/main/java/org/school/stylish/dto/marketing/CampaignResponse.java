package org.school.stylish.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"product_id", "picture", "story"})
public class CampaignResponse {
    @JsonProperty("product_id")
    private Long id;
    private String story;
    @JsonProperty("picture")
    private String pictureUrl;
}
