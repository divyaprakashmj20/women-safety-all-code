package com.tus.sosmanagement.service;


import com.tus.sosmanagement.dto.NearbyUsersData;
import com.tus.sosmanagement.dto.SosLocationData;
import com.tus.sosmanagement.dto.SosRegisterDTO;
import com.tus.sosmanagement.dto.SosUpdateDTO;
import com.tus.sosmanagement.entity.SosEntity;
import com.tus.sosmanagement.entity.SosHelpUserEntity;
import com.tus.sosmanagement.enums.HelpState;
import com.tus.sosmanagement.enums.SosState;
import com.tus.sosmanagement.repository.SosHelpUserRepo;
import com.tus.sosmanagement.repository.SosRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public SosEntity registerSos(SosRegisterDTO sosRegisterDTO){
        SosEntity sosEntity = new SosEntity();
        BeanUtils.copyProperties(sosRegisterDTO, sosEntity);
        sosEntity.setSosRegistrationTime(LocalDateTime.now());
        sosEntity.setSosState(SosState.ACTIVE);
        sosEntity.setSosLastUpdateTime(LocalDateTime.now());
        SosEntity savedSosEntity = sosRepository.save(sosEntity);
        microServiceExecutorService.getRelatives(savedSosEntity);
        //notify relative users
        microServiceExecutorService.sendSosToCachingService(new SosLocationData(sosRegisterDTO.getUserId(),savedSosEntity.getSosId(), savedSosEntity.getLatitude(), savedSosEntity.getLongitude()));
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
        SosEntity sosEntity = sosRepository.getById(sos_id);
        sosEntity.setSosState(SosState.COMPLETED);
        sosRepository.save(sosEntity);
        List<SosHelpUserEntity> sosHelpUserEntityList = sosHelpUserRepo.findAllUsersBySosId(sos_id);
        for(SosHelpUserEntity sosHelpUserEntity : sosHelpUserEntityList){
            sosHelpUserEntity.setHelpState(HelpState.EXPIRED);
            sosHelpUserRepo.save(sosHelpUserEntity);
        }

        RestTemplate restTemplate = new RestTemplate();
        String userServiceURL = "http://localhost:8085/sos/delete/" + sos_id;
        restTemplate.delete(userServiceURL);

    }

    public List<SosHelpUserEntity> getAllNearbySosRelatedToUser(Long user_id) {
        return sosHelpUserRepo.findAllActiveSosByUserId(user_id);
    }

    public void helpSos(Long sos_id, Long user_id) {
        SosHelpUserEntity sosHelpUserEntity = sosHelpUserRepo.getById(new SosHelpUserEntity.SosUserId(sos_id,user_id));
        if(sosHelpUserEntity.getHelpState() == HelpState.NA){
            sosHelpUserEntity.setHelpState(HelpState.ACTIVE);
            sosHelpUserRepo.save(sosHelpUserEntity);
        }
    }
}
