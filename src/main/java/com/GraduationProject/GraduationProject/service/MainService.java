package com.GraduationProject.GraduationProject.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FLASK_API_URL = "http://127.0.0.1:5000/predict";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String predictRecyclableMaterial(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(FLASK_API_URL, requestEntity, String.class);


        Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        List<Map<String, Object>> detections = (List<Map<String, Object>>) responseMap.get("detections");
        Map<String, Object> classCounts = (Map<String, Object>) responseMap.get("class_counts"); // Sınıf adetlerini al


        Map<String, Object> result = new HashMap<>();
        result.put("detections", detections);
        result.put("class_counts", classCounts);

        return objectMapper.writeValueAsString(result);
    }
}