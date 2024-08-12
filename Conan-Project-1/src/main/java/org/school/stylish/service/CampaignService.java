package org.school.stylish.service;

import org.school.stylish.dto.marketing.CampaignRequest;
import org.school.stylish.dto.marketing.CampaignResponse;
import org.school.stylish.dto.other.ApiResponse;

import java.util.List;

public interface CampaignService {
    boolean savaCampaign(CampaignRequest campaignRequest);

    ApiResponse<List<CampaignResponse>> getCampaigns();
}
