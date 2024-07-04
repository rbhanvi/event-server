package com.tietoevry.serverskeletonjava.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TokenClient {

    @Value("${token.username}")
    private String username;

    @Value("${token.password}")
    private String password;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getToken() throws JsonProcessingException {
        String url = "http://localhost:8080/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());

        return jsonNode.get("token").asText();
    }
}
