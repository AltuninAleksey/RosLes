package com.rosles.forestcrops.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class HttpQueryService {

    public String postQuery(String url, Object request, String token) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if(token != null && !token.isEmpty()){
            headers.set("Authorization", "Bearer " + token);
        }

        ObjectMapper mapper = new ObjectMapper();

        String requestBody = mapper.writeValueAsString(request);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        String response = responseEntity.getBody();

        return response;
    }

    public ResponseEntity<String> getQueryGetResponseEntity(String url, Object request, String token) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if(token != null && !token.isEmpty()){
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return responseEntity;
    }

}
