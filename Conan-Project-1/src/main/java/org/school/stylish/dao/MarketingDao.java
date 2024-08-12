package org.school.stylish.dao;

import org.school.stylish.dto.marketing.CampaignRequest;
import org.school.stylish.dto.marketing.CampaignResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MarketingDao {

    boolean insertOrUpdateCampaign(CampaignRequest campaignRequest);

    String saveFile(MultipartFile file);

    List<CampaignResponse> findAllCampaigns();
}
