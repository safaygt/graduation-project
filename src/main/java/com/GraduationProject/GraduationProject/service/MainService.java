package com.GraduationProject.GraduationProject.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.GraduationProject.GraduationProject.entity.Product;
import com.GraduationProject.GraduationProject.entity.ProductType;
import com.GraduationProject.GraduationProject.entity.Recycle;
import com.GraduationProject.GraduationProject.entity.Usr;
import com.GraduationProject.GraduationProject.repo.ProductRepo;
import com.GraduationProject.GraduationProject.repo.ProductTypeRepo;
import com.GraduationProject.GraduationProject.repo.RecycleRepo;
import com.GraduationProject.GraduationProject.repo.UsrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FLASK_API_URL = "http://model:5000/predict";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductTypeRepo productTypeRepo;

    @Autowired
    private RecycleRepo recycleRepo;

    @Autowired
    private UsrRepo usrRepo;

    @Transactional
    public String predictAndSave(MultipartFile file) throws IOException {
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
        Map<String, Object> classCounts = (Map<String, Object>) responseMap.get("class_counts");
        String processedImageUrl = (String) responseMap.get("processed_image_url");


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usr usr = usrRepo.findByUsername(userDetails.getUsername());

        Recycle recycle = new Recycle();
        recycle.setUsr(usr);
        recycle.setRecycleName(usr.getUsername() + "_recycle_" + recycleRepo.count());
        recycleRepo.save(recycle);

        double totalEffect = 0.0;
        Map<String, Double> classEffects = new HashMap<>();

        for (Map.Entry<String, Object> entry : classCounts.entrySet()) {
            String className = entry.getKey();
            int count = (int) entry.getValue();

            ProductType productType = productTypeRepo.findByProductName(className);

            if (productType != null) {
                Product product = new Product();
                product.setFkproductType(productType);
                product.setFkRecycle(recycle);
                product.setCount(count);
                productRepo.save(product);

                double effect = productType.getEffect();
                totalEffect += count * effect;
                classEffects.put(className, effect);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("detections", detections);
        result.put("class_counts", classCounts);
        result.put("total_effect", totalEffect);
        result.put("class_effects", classEffects);
        result.put("processed_image_url", processedImageUrl);

        return objectMapper.writeValueAsString(result);
    }
}