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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@EnableGlobalMethodSecurity(prePostEnabled=true)
@CrossOrigin(origins = "*")
public class SosController {

    @Autowired
    SosService sosService;

    @Autowired
    SosRepository sosRepository;

    @Autowired
    SosHelpUserRepo sosHelpUserRepo;


    @PostMapping("/sos")
    public ResponseEntity<?> registerSos(@RequestBody SosRegisterDTO sosRegisterDTO, @RequestHeader (name="Authorization") String token){
        SosEntity sosEntity = sosService.registerSos(sosRegisterDTO, token);
        return new ResponseEntity<>(sosEntity, HttpStatus.OK);
    }

    @GetMapping("/sos/{id}")
    @PreAuthorize("@preAuthorisationService.checkUserId(#id,#token)")
    public ResponseEntity<?> getActiveSos(@PathVariable Long id, @RequestHeader (name="Authorization") String token){
        SosEntity sosEntity = sosService.getActiveSos(id);
        return new ResponseEntity<>(sosEntity, HttpStatus.OK);
    }

    @GetMapping("/sos/location/{sos_id}")
    @PreAuthorize("@preAuthorisationService.checkId(#sos_id,#token)")
    public SosEntity getSosLocation(@PathVariable long sos_id, @RequestHeader (name="Authorization") String token){
        SosEntity sosEntity = sosRepository.findById(sos_id).get();
        return sosEntity;
    }



    @PutMapping("/sos/location")
    @PreAuthorize("@preAuthorisationService.updateCheck(#sosUpdateDTO.getUserId(),#token)")
    public ResponseEntity<?> updateSosDetails(@RequestBody SosUpdateDTO sosUpdateDTO, @RequestHeader (name="Authorization") String token){
        SosEntity sosEntity = sosService.updateSosDetails(sosUpdateDTO);
        return new ResponseEntity<>(sosEntity, HttpStatus.OK);
    }

    @DeleteMapping("/sos/{sos_id}")
    public ResponseEntity<?> deleteSos(@PathVariable Long sos_id){
        sosService.deleteSos(sos_id);
        return new ResponseEntity<>(new ApplicationMessage("Success"), HttpStatus.OK);
    }


}
