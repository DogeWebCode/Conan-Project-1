package org.school.stylish.rowmapper;

import org.school.stylish.dto.marketing.CampaignResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignRowMapper implements RowMapper<CampaignResponse> {
    @Override
    public CampaignResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        CampaignResponse campaignResponse = new CampaignResponse();
        campaignResponse.setId(rs.getLong("product_id"));
        campaignResponse.setStory(rs.getString("story"));
        campaignResponse.setPictureUrl(rs.getString("picture"));
        return campaignResponse;
    }
}
