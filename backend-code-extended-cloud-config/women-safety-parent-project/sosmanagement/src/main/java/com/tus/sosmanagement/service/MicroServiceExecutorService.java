package com.tus.sosmanagement.service;

import com.tus.sosmanagement.VO.RelativeVO;
import com.tus.sosmanagement.dto.SosLocationData;
import com.tus.sosmanagement.entity.RelativeEntity;
import com.tus.sosmanagement.entity.SosEntity;
import com.tus.sosmanagement.repository.RelativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class MicroServiceExecutorService {

    @Autowired
    RelativeRepository relativeRepository;

    @Autowired
    RestTemplate restTemplate;

    @Async
    public CompletableFuture<ResponseEntity<RelativeVO>> getRelatives(SosEntity savedSosEntity, String token){
        // RestTemplate restTemplate = new RestTemplate();
        System.out.println(Thread.currentThread().getName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);


//        String userServiceURL = "http://localhost:8081/user/" + savedSosEntity.getUserId();

        String userServiceURL = "http://USER-SERVICE/user/" + savedSosEntity.getUserId();
//        RelativeVO relativeVO = restTemplate.getForObject(userServiceURL, entity, RelativeVO.class);



        ResponseEntity<RelativeVO> relativeVO = restTemplate.exchange(
                userServiceURL, HttpMethod.GET, entity, RelativeVO.class);
//        System.out.println(relativeVO);
        for(RelativeVO.Relative relative : relativeVO.getBody().getResponse().getRelatives()){
            RelativeEntity relativeEntity = new RelativeEntity();
            relativeEntity.setRelativeEntityId(new RelativeEntity.RelativeEntityId(savedSosEntity.getSosId(), relative.getId()));
            relativeRepository.save(relativeEntity);
        }
        return CompletableFuture.completedFuture(relativeVO);
    }

    @Async
    public CompletableFuture<String> sendSosToCachingService(SosLocationData sosLocationData){
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(Thread.currentThread().getName());
        String userServiceURL = "http://localhost:8084/sos/location";
        restTemplate.postForEntity(userServiceURL, sosLocationData, Object.class);
        return CompletableFuture.completedFuture("Success");
    }

}
