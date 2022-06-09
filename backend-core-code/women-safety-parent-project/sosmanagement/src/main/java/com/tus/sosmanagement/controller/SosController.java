package com.tus.sosmanagement.controller;

import com.tus.sosmanagement.dto.ApplicationMessage;
import com.tus.sosmanagement.dto.NearbyUsersData;
import com.tus.sosmanagement.dto.SosRegisterDTO;
import com.tus.sosmanagement.dto.SosUpdateDTO;
import com.tus.sosmanagement.entity.SosEntity;
import com.tus.sosmanagement.entity.SosHelpUserEntity;
import com.tus.sosmanagement.enums.HelpState;
import com.tus.sosmanagement.repository.SosHelpUserRepo;
import com.tus.sosmanagement.repository.SosRepository;
import com.tus.sosmanagement.service.SosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SosController {

    @Autowired
    SosService sosService;

    @Autowired
    SosRepository sosRepository;

    @Autowired
    SosHelpUserRepo sosHelpUserRepo;


    @PostMapping("/sos")
    public ResponseEntity<?> registerSos(@RequestBody SosRegisterDTO sosRegisterDTO){
        SosEntity sosEntity = sosService.registerSos(sosRegisterDTO);
        return new ResponseEntity<>(sosEntity, HttpStatus.OK);
    }

    @GetMapping("/sos/{id}")
    public ResponseEntity<?> getActiveSos(@PathVariable Long id){
        SosEntity sosEntity = sosService.getActiveSos(id);
        return new ResponseEntity<>(sosEntity, HttpStatus.OK);
    }

    @GetMapping("/sos/location/{sos_id}")
    public SosEntity getSosLocation(@PathVariable long sos_id){
        SosEntity sosEntity = sosRepository.getById(sos_id);
        return sosEntity;
    }



    @PutMapping("/sos/location")
    public ResponseEntity<?> updateSosDetails(@RequestBody SosUpdateDTO sosUpdateDTO){
        SosEntity sosEntity = sosService.updateSosDetails(sosUpdateDTO);
        return new ResponseEntity<>(sosEntity, HttpStatus.OK);
    }

    @PostMapping("/sos/nearby")
    public String associateSosWithUser(@RequestBody List<NearbyUsersData> nearbyUsersData){
        sosService.associateSosWithUser(nearbyUsersData);
        return "Success";
    }

    @DeleteMapping("/sos/{sos_id}")
    public ResponseEntity<?> deleteSos(@PathVariable Long sos_id){
        sosService.deleteSos(sos_id);
        return new ResponseEntity<>(new ApplicationMessage("Success"), HttpStatus.OK);
    }

    @GetMapping("/sos/nearby/{user_id}")
    public ResponseEntity<?> getAllNearbySosRelatedToUser(@PathVariable Long user_id){
        List<SosHelpUserEntity> sosHelpUserEntityList = sosService.getAllNearbySosRelatedToUser(user_id);
        return new ResponseEntity<>(sosHelpUserEntityList, HttpStatus.OK);
    }

    @PutMapping("/sos/nearby/{sos_id}/{user_id}")
    public ResponseEntity<?> helpSos(@PathVariable Long sos_id, @PathVariable Long user_id){
        sosService.helpSos(sos_id,user_id);
        return new ResponseEntity<>(new ApplicationMessage("Success"), HttpStatus.OK);
    }

    @PutMapping("/sos/location/{sos_id}/{user_id}")
    public void stopSosHelpByUSer(@PathVariable long sos_id, @PathVariable long user_id){
        SosHelpUserEntity sosHelpUserEntity = sosHelpUserRepo.getById(new SosHelpUserEntity.SosUserId(sos_id,user_id));
        sosHelpUserEntity.setHelpState(HelpState.NA);
        sosHelpUserRepo.save(sosHelpUserEntity);
    }

}
