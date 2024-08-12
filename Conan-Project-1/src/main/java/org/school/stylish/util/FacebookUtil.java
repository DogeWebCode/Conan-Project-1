package org.school.stylish.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dto.user.facebook.FacebookUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Log4j2
@Component
public class FacebookUtil {
    @Value("${facebook.appId}")
    private String appId;

    @Value("${facebook.appSecret}")
    private String appSecret;

    private final RestTemplate restTemplate;

    public FacebookUtil(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public FacebookUserDTO getUserInfo(String accessToken) {
        String fields = "id,name,picture{url},email";
        String urlString = String.format("https://graph.facebook.com/v20.0/me?fields=%s&access_token=%s",
                URLEncoder.encode(fields, StandardCharsets.UTF_8),
                URLEncoder.encode(accessToken, StandardCharsets.UTF_8));

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.toString(), FacebookUserDTO.class);
        } catch (Exception e) {
            log.error("Error occurred while fetching Facebook user info", e);
            throw new RuntimeException("Failed to fetch Facebook user info", e);
        }
    }


    public boolean verifyAccessToken(String accessToken) {
        String appAccessToken = appId + "|" + appSecret;
        String url = String.format("https://graph.facebook.com/debug_token?input_token=%s&access_token=%s",
                accessToken, appAccessToken);
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
                return data != null && Boolean.TRUE.equals(data.get("is_valid"));
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error verifying access token with Facebook");
        }
        return false;
    }
}
