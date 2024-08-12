package org.school.stylish.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.MarketingDao;

import org.school.stylish.dto.marketing.CampaignRequest;
import org.school.stylish.dto.marketing.CampaignResponse;
import org.school.stylish.dto.other.ApiResponse;
import org.school.stylish.service.CampaignService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class MarketingServiceImpl implements CampaignService {

    private final MarketingDao marketingDao;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CAMPAIGNS_CACHE_KEY = "campaigns";
    private static final Duration CACHE_TTL = Duration.ofMinutes(10);

    @Override
    public boolean savaCampaign(CampaignRequest campaignRequest) {
        boolean updated = marketingDao.insertOrUpdateCampaign(campaignRequest);
        try {
            if (updated) {
                redisTemplate.delete(CAMPAIGNS_CACHE_KEY);
                log.info("Cache cleared!!!");
            }
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            log.warn("Error while clearing cache: {}", e.getMessage());
        }
        return updated;
    }

    @Override
    public ApiResponse<List<CampaignResponse>> getCampaigns() {
        if (isRedisAvailable()) {
            Object cachedValue = redisTemplate.opsForValue().get(CAMPAIGNS_CACHE_KEY);
            if (cachedValue instanceof ApiResponse) {
                ApiResponse<List<CampaignResponse>> campaignFromRedis = (ApiResponse<List<CampaignResponse>>) cachedValue;
                if (campaignFromRedis.getData() != null) {
                    log.info("Cache hit");
                    return campaignFromRedis;
                }
            }
        }

        log.info("Cache miss");
        try {
            List<CampaignResponse> campaignFromDB = marketingDao.findAllCampaigns();
            log.info("get campaigns from db");

            ApiResponse<List<CampaignResponse>> response = new ApiResponse<>(campaignFromDB);

            if (isRedisAvailable()) {
                try {
                    redisTemplate.opsForValue().set(CAMPAIGNS_CACHE_KEY, response, CACHE_TTL);
                    log.info("Campaigns added to RedisCache");
                } catch (Exception e) {
                    log.warn("Failed to set cache: {}", e.getMessage());
                }
            }
            return response;
        } catch (DataAccessException e) {
            log.error("Failed to fetch campaigns: {}", e.getMessage());
            throw new RuntimeException("Unable to fetch campaigns from database", e);
        }
    }

    private boolean isRedisAvailable() {
        try {
            Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().ping();
            return true;
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            log.debug("Redis is not available: {}", e.getMessage());
            return false;
        }
    }
}