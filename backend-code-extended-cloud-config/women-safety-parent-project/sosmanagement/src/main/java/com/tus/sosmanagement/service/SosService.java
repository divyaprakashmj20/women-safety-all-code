package com.tus.sosmanagement.service;


import com.tus.sosmanagement.VO.RelativeVO;
import com.tus.sosmanagement.dto.NearbyUsersData;
import com.tus.sosmanagement.dto.SosLocationData;
import com.tus.sosmanagement.dto.SosRegisterDTO;
import com.tus.sosmanagement.dto.SosUpdateDTO;
import com.tus.sosmanagement.entity.RelativeEntity;
import com.tus.sosmanagement.entity.SosEntity;
import com.tus.sosmanagement.entity.SosHelpUserEntity;
import com.tus.sosmanagement.enums.HelpState;
import com.tus.sosmanagement.enums.SosState;
import com.tus.sosmanagement.repository.RelativeRepository;
import com.tus.sosmanagement.repository.SosHelpUserRepo;
import com.tus.sosmanagement.repository.SosRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SosService {

    @Autowired
    SosRepository sosRepository;

    @Autowired
    MicroServiceExecutorService microServiceExecutorService;

    @Autowired
    SosHelpUserRepo sosHelpUserRepo;

    @Autowired
    RestTemplate restTemplate;



    @Autowired
    RelativeRepository relativeRepository;

    @Transactional
    public SosEntity registerSos(SosRegisterDTO sosRegisterDTO, String token){
        SosEntity sosEntity = new SosEntity();
        BeanUtils.copyProperties(sosRegisterDTO, sosEntity);
        sosEntity.setSosRegistrationTime(LocalDateTime.now());
        sosEntity.setSosState(SosState.ACTIVE);
        sosEntity.setSosLastUpdateTime(LocalDateTime.now());
        SosEntity savedSosEntity = sosRepository.save(sosEntity);
//        microServiceExecutorService.getRelatives(savedSosEntity,token);
        //notify relative users
//        microServiceExecutorService.sendSosToCachingService(new SosLocationData(sosRegisterDTO.getUserId(),savedSosEntity.getSosId(), savedSosEntity.getLatitude(), savedSosEntity.getLongitude()));

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
        return savedSosEntity;
    }

    public SosEntity getActiveSos(Long id) {
        return sosRepository.getByUserIdAndSosState(id,SosState.ACTIVE);
    }

    public SosEntity updateSosDetails(SosUpdateDTO sosUpdateDTO) {
        Optional<SosEntity> sosEntityOptional = sosRepository.findById(sosUpdateDTO.getSosId());
        SosEntity sosEntity = sosEntityOptional.get();
        sosEntity.setLatitude(sosUpdateDTO.getLatitude());
        sosEntity.setLongitude(sosUpdateDTO.getLongitude());
        sosEntity.setSosLastUpdateTime(LocalDateTime.now());
        return sosRepository.save(sosEntity);
    }

    public void associateSosWithUser(List<NearbyUsersData> nearbyUsersDataList) {
        for(NearbyUsersData nearbyUsersData : nearbyUsersDataList){

            sosHelpUserRepo.save(new SosHelpUserEntity(
                    new SosHelpUserEntity.SosUserId(nearbyUsersData.getSos_id(), nearbyUsersData.getUser_id()),
                    nearbyUsersData.getLatitude(),
                    nearbyUsersData.getLongitude(),
                    null,
                    LocalDateTime.now(),
                    HelpState.NA
            ));
        }
    }

    public void deleteSos(Long sos_id) {
        SosEntity sosEntity = sosRepository.findById(sos_id).get();
        sosEntity.setSosState(SosState.COMPLETED);
        sosRepository.save(sosEntity);
        for(Long i : relativeRepository.findBySosId(sos_id)){
            relativeRepository.delete(relativeRepository.findById(new RelativeEntity.RelativeEntityId(sos_id,i)).get());;
//         relativeRepository.delete();
        }
//        List<SosHelpUserEntity> sosHelpUserEntityList = sosHelpUserRepo.findAllUsersBySosId(sos_id);
//        for(SosHelpUserEntity sosHelpUserEntity : sosHelpUserEntityList){
//            sosHelpUserEntity.setHelpState(HelpState.EXPIRED);
//            sosHelpUserRepo.save(sosHelpUserEntity);
//        }

//        RestTemplate restTemplate = new RestTemplate();
//        String userServiceURL = "http://localhost:8085/sos/delete/" + sos_id;
//        restTemplate.delete(userServiceURL);

    }

    public List<SosHelpUserEntity> getAllNearbySosRelatedToUser(Long user_id) {
        return sosHelpUserRepo.findAllActiveSosByUserId(user_id);
    }

    public void helpSos(Long sos_id, Long user_id) {
        SosHelpUserEntity sosHelpUserEntity = sosHelpUserRepo.findById(new SosHelpUserEntity.SosUserId(sos_id,user_id)).get();
        if(sosHelpUserEntity.getHelpState() == HelpState.NA){
            sosHelpUserEntity.setHelpState(HelpState.ACTIVE);
            sosHelpUserRepo.save(sosHelpUserEntity);
        }
    }
}
