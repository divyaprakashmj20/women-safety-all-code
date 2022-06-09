package com.tus.womensafety.service;

import com.tus.womensafety.config.JwtUtil;
import com.tus.womensafety.dto.SecurityContextDTO;
import com.tus.womensafety.entity.UserEntity;
import com.tus.womensafety.repository.RelativeRepository;
import com.tus.womensafety.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreAuthorisationService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    public boolean checkId(Long id, String token){

        System.out.println(token);
        String username = jwtUtil.extractusername(token.replace("Bearer ",""));
        List<UserEntity> userEntity = userRepository.findByEmail(username);
        if(userEntity.size()>0){
            if(userEntity.get(0).getRole().equalsIgnoreCase("ADMIN")){
                return true;
            }else{
                if(userEntity.get(0).getId().equals(id)){
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }

}
