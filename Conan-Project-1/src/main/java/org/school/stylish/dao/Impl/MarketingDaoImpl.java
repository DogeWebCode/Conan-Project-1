package org.school.stylish.dao.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.school.stylish.dao.MarketingDao;
import org.school.stylish.dto.marketing.CampaignRequest;
import org.school.stylish.dto.marketing.CampaignResponse;
import org.school.stylish.exception.InsertException;
import org.school.stylish.rowmapper.CampaignRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Log4j2
public class MarketingDaoImpl implements MarketingDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseUrl;

    @Override
    public boolean insertOrUpdateCampaign(CampaignRequest campaignRequest) {
        String upsertCampaignSql = "INSERT INTO campaign (product_id, story, picture) VALUES (?,?,?) " +
                "ON DUPLICATE KEY UPDATE story = VALUES(story), picture = VALUES(picture)";
        try {
            int affectedRows = jdbcTemplate.update(upsertCampaignSql,
                    campaignRequest.getProductId(),
                    campaignRequest.getStory(),
                    saveFile(campaignRequest.getPicture()));

            log.info("{} campaign for product ID: {}",
                    affectedRows == 1 ? "Inserted new" : "Updated existing",
                    campaignRequest.getProductId());

            return true;
        } catch (DataAccessException e) {
            log.error("Failed to insert or update campaign: {}", e.getMessage());
            throw new InsertException("Failed to insert or update campaign", e);
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean mkdirResult = dir.mkdirs();
            if (!mkdirResult) {
                log.error("Failed to create directories: {}", uploadDir);
                throw new InsertException("Failed to create directories: " + uploadDir);
            }
        }

        String filePath = uploadDir + file.getOriginalFilename();
        File destFile = new File(filePath);
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
        } catch (IOException e) {
            log.error("Failed to save file: {}", e.getMessage());
            log.error("Upload directory: {}", uploadDir);
            log.error("Destination file path: {}", filePath);
            throw new InsertException("Failed to save file", e);
        }
        return baseUrl + filePath;
    }

    @Override
    public List<CampaignResponse> findAllCampaigns() {
        String findAllCampaignSql = "SELECT * FROM campaign";
        return jdbcTemplate.query(findAllCampaignSql, new CampaignRowMapper());
    }
}
