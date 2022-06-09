package com.tus.womensafety;

import com.tus.womensafety.entity.RelativeEntity;
import com.tus.womensafety.entity.UserEntity;
import com.tus.womensafety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostConstructExampleBean {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        System.out.println("sda");
        List<UserEntity> userEntity = userRepository.findByEmail("admin@gmail.com");
        if(userEntity.size() == 0){
            ArrayList<RelativeEntity> f = new ArrayList<>();
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setName("admin");
            userEntity1.setRelatives(f);
            userEntity1.setRole("ADMIN");
            userEntity1.setEmail("admin@gmail.com");
            userEntity1.setPhoneNumber("9999999999");
            userEntity1.setPassword(passwordEncoder.encode("password"));
            userRepository.save(userEntity1);
        }
    }
}
