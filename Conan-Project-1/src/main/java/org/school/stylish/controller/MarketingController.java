package org.school.stylish.controller;

import lombok.RequiredArgsConstructor;
import org.school.stylish.dto.marketing.CampaignRequest;
import org.school.stylish.dto.marketing.CampaignResponse;
import org.school.stylish.dto.other.ApiResponse;
import org.school.stylish.dto.other.ErrorResponse;
import org.school.stylish.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1.0/marketing")
public class MarketingController {
    
    private final CampaignService campaignService;

    @PostMapping("/addCampaign")
    public ResponseEntity<?> addCampaign(
            @RequestParam("productId") Long productId,
            @RequestParam("story") String story,
            @RequestParam("picture") MultipartFile picture
    ) {
        CampaignRequest campaignRequest = new CampaignRequest(productId, story, picture);
        boolean success = campaignService.savaCampaign(campaignRequest);
        if (success) {
            return ResponseEntity.ok().body("{\"status\": \"success\", \"message\": \"成功新增!!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred while adding the campaign"));
        }
    }

    @GetMapping("/campaigns")
    public ResponseEntity<?> getCampaigns() {
        ApiResponse<List<CampaignResponse>> campaigns = campaignService.getCampaigns();
        return ResponseEntity.ok().body(campaigns);
    }
}

