package com.tus.sosmanagement.service;


import com.tus.sosmanagement.VO.RelativeVO;
import com.tus.sosmanagement.config.JwtUtil;
import com.tus.sosmanagement.dto.UserEntityResponseDTO;
import com.tus.sosmanagement.dto.UserResponseDTO;
import com.tus.sosmanagement.dto.UserResponseDTOList;
import com.tus.sosmanagement.repository.RelativeRepository;
import com.tus.sosmanagement.repository.SosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PreAuthorisationService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RestTemplate restTemplate;



    @Autowired
    SosRepository sosRepository;

    @Autowired
    RelativeRepository relativeRepository;

//    @Autowired
//    UserRepository userRepository;
//


    public boolean updateCheck(Long user_id, String token){
        String user_id_from_token = jwtUtil.extractUserId(token.replace("Bearer ",""));
        if(user_id_from_token.equals(Long.toString(user_id))){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUserId(Long checkUserId, String token) {
        // RestTemplate restTemplate = new RestTemplate();
//        System.out.println(Thread.currentThread().getName());

        String username = jwtUtil.extractusername(token.replace("Bearer ",""));



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);


        String userServiceURL = "http://USER-SERVICE/user/" + checkUserId;
        ResponseEntity<UserEntityResponseDTO> userResponseDTOResponseEntity = restTemplate.exchange(
                userServiceURL, HttpMethod.GET, entity, UserEntityResponseDTO.class);
//

//        System.out.println(userResponseDTOResponseEntity.getBody().getResponse().getId());
//        UserResponseDTO userResponseDTO = userResponseDTOResponseEntity.getBody().getResponse();

        if(userResponseDTOResponseEntity.getBody().getResponse().getRole().equals("ADMIN")){
            return true;
        }else{
            if(userResponseDTOResponseEntity.getBody().getResponse().getId().equals(checkUserId)){
                return true;
            }else{
                return false;
            }
        }

//        return true;


        }

        public boolean checkId(Long sos_id, String token) {
        // RestTemplate restTemplate = new RestTemplate();
//        System.out.println(Thread.currentThread().getName());

        String username = jwtUtil.extractusername(token.replace("Bearer ",""));



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);


        String userServiceURL = "http://USER-SERVICE/user/email/" + username;
        ResponseEntity<UserResponseDTO[]> userResponseDTOResponseEntity = restTemplate.exchange(
                userServiceURL, HttpMethod.GET, entity, UserResponseDTO[].class);
//

        System.out.println(userResponseDTOResponseEntity);
        UserResponseDTO userResponseDTO = userResponseDTOResponseEntity.getBody()[0];

        if(userResponseDTO.getRole().equals("ADMIN")){
            return true;
        }else{
            if(relativeRepository.findBySosId(sos_id).contains(userResponseDTO.getId())){
                return true;
            }else{
                if(sosRepository.findById(sos_id).get().getUserId().equals(userResponseDTO.getId())){
                    return true;
                }else{
                    return false;
                }
            }
        }


//        RelativeVO relativeVO = restTemplate.getForObject(userServiceURL, entity, RelativeVO.class);
//    return true;
    }

//
//        System.out.println(token);
//        String username = jwtUtil.extractusername(token.replace("Bearer ",""));
//        List<UserEntity> userEntity = userRepository.findByEmail(username);
//        if(userEntity.size()>0){
//            if(userEntity.get(0).getRole().equalsIgnoreCase("ADMIN")){
//                return true;
//            }else{
//                if(userEntity.get(0).getId().equals(id)){
//                    return true;
//                }else{
//                    return false;
//                }
//            }
//        }else{
//            return false;
//        }
//    }

}
