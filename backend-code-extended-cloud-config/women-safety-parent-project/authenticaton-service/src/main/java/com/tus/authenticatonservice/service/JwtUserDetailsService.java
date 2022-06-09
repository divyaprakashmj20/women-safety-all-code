package com.tus.authenticatonservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.tus.authenticatonservice.entity.UserEntity;
import com.tus.authenticatonservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity userOptional = userRepository.findByEmail(username);
		System.out.println(username);
//		if(userOptional.isPresent()) {
//			UserEntity user = userOptional.get();
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

			authorities.add(new SimpleGrantedAuthority(userOptional.getRole()));



			return new User(userOptional.getEmail().toString(), userOptional.getPassword().toString(), authorities);
//		}
//		return null;
	}

}