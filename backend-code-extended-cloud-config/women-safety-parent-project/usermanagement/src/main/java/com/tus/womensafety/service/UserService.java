package com.tus.womensafety.service;

import com.tus.womensafety.dto.UserDTO;
import com.tus.womensafety.dto.UserPostDTO;
import com.tus.womensafety.dto.UserResponseDTO;
import com.tus.womensafety.entity.RelativeEntity;
import com.tus.womensafety.entity.UserEntity;
import com.tus.womensafety.repository.RelativeRepository;
import com.tus.womensafety.repository.UserRepository;
import com.tus.womensafety.utils.BeanCopyUtitliy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RelativeRepository relativeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponseDTO getUserById(Long id){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(userEntity.get(), userResponseDTO);
        List<RelativeEntity> friendList = relativeRepository.findByUserEntity(userEntity.get());
        List<UserEntity> userEntityList = new ArrayList<>();
        friendList.stream().forEach(i -> {
            userEntityList.add(i.getRelative());
        });
        userResponseDTO.setRelatives(userEntityList);
        return userResponseDTO;
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> userEntity = userRepository.findAll();
        return userEntity;
    }

    public List<UserResponseDTO> getRelativesById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        List<RelativeEntity> friendList = relativeRepository.findByUserEntity(userEntity.get());
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        friendList.stream().forEach(i -> {
            userResponseDTOList.add(UserResponseDTO.builder().id(i.getRelative().getId())
                    .name(i.getRelative().getName())
                    .email(i.getRelative().getEmail())
                    .phoneNumber(i.getRelative().getPhoneNumber()).build());
        });
        return userResponseDTOList;

    }

    public List<UserResponseDTO> getAllUsersWithRelatives() {

        List<UserEntity> userEntity = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        userEntity.stream().forEach(u -> {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            BeanUtils.copyProperties(u, userResponseDTO);
            List<RelativeEntity> friendList = relativeRepository.findByUserEntity(u);
            List<UserEntity> userEntityList = new ArrayList<>();
            friendList.stream().forEach(i -> {
                userEntityList.add(i.getRelative());
            });
            userResponseDTO.setRelatives(userEntityList);
            userResponseDTOS.add(userResponseDTO);
        });
        return userResponseDTOS;
    }

    public UserResponseDTO addUser(UserPostDTO userPostDTO) {
        List<Long> l = userPostDTO.getRelatives();
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userPostDTO, userEntity);
        ArrayList<UserEntity> ll = new ArrayList<>();
        ArrayList<RelativeEntity> f = new ArrayList<>();
        if (l != null && l.size() > 0) {
            l.stream().forEach(i -> {
                RelativeEntity ff = new RelativeEntity(userEntity, userRepository.findById(i).get());
                f.add(ff);
            });
        }
        userEntity.setRelatives(f);
        System.out.println(userEntity.getPassword());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("USER");
        UserEntity u = userRepository.save(userEntity);
        return getUserById(u.getId());
    }

    @Transactional
    public void deleteUser( Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
//        System.out.println(userEntity);
//        List<RelativeEntity> friendList = relativeRepository.findByUserEntity(userEntity);
//        System.out.println(friendList);
//        friendList.stream().forEach(i -> {
            relativeRepository.deleteByRelative(userEntity);
//        });
//        friendList.stream().forEach(i -> {
            relativeRepository.deleteByUserEntity(userEntity);
//        });
        userRepository.deleteById(id);



    }

    @Transactional
    public void deleteUserRelative(Long userid, Long relativeid) {
        UserEntity userEntity = userRepository.findById(userid).get();
        UserEntity friend = userRepository.findById(relativeid).get();
        relativeRepository.deleteByUserEntityAndRelative(userEntity, friend);
    }

    @Transactional
    public UserResponseDTO updateUserDetailsById(Long userid, UserDTO userDTO) {
        List<Long> l = userDTO.getRelatives();
        UserEntity userEntity = userRepository.findById(userid).get();
        System.out.println(BeanCopyUtitliy.getNullPropertyNames(userDTO)[0]);
        BeanUtils.copyProperties(userDTO, userEntity, BeanCopyUtitliy.getNullPropertyNames(userDTO));
        userEntity.setId(userid);
        ArrayList<RelativeEntity> f = new ArrayList<>();
        if(l != null && l.size() == 0){
            List<RelativeEntity> friendList = relativeRepository.findByUserEntity(userEntity);
            friendList.stream().forEach(i -> {
                relativeRepository.deleteByUserEntityAndRelative(userEntity, i.getRelative());
            });
        }
        if (l != null && l.size() > 0) {
            List<RelativeEntity> friendList = relativeRepository.findByUserEntity(userEntity);
            friendList.stream().forEach(i -> {
                relativeRepository.deleteByUserEntityAndRelative(userEntity, i.getRelative());
            });
            l.stream().forEach(i -> {
                relativeRepository.save(new RelativeEntity(userEntity, userRepository.findById(i).get()));
            });
        }
        return getUserById(userRepository.save(userEntity).getId());

    }


    public List<UserResponseDTO> getAllUsersWhoHasCurrentUserAsRelative(Long id) {
        return null;
    }

    public UserResponseDTO addUserRelative(Long userid, Long relativeid) {
        relativeRepository.save(new RelativeEntity(userRepository.findById(userid).get(),userRepository.findById(relativeid).get()));
        return getUserById(userid);
    }
}
