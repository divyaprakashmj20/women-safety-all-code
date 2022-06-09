package com.tus.sosmanagement.controller;

import com.tus.sosmanagement.entity.SosEntity;
import com.tus.sosmanagement.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class RelativeController {


    @Autowired
    RelativeService relativeService;

    @GetMapping("/relatives/sos/{relative_id}")
    @PreAuthorize("@preAuthorisationService.checkUserId(#relative_id,#token)")
    public ArrayList<SosEntity> getRelativesInDanger(@PathVariable Long relative_id, @RequestHeader(name="Authorization") String token){
        return relativeService.getRelativesInDanger(relative_id);
    }



}
