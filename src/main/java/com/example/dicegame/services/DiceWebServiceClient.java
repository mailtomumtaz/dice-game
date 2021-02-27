package com.example.dicegame.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Class: DiceWebServiceClient
 * Communicate with external service
 */
@Component
public class DiceWebServiceClient {

    @Value("${DICE_ROLL_SERVICE_URL}")
    private String serviceUrl;

    public int getDiceOutcome() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.getForEntity(serviceUrl, String.class);
        if (response.hasBody()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            return node.get("score").asInt();
        }
        return 0;
    }
}