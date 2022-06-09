package com.tus.authenticatonservice.controller;

import java.util.ArrayList;
import java.util.Objects;

import com.tus.authenticatonservice.config.JwtTokenUtil;
import com.tus.authenticatonservice.entity.UserEntity;
import com.tus.authenticatonservice.model.JwtRequest;
import com.tus.authenticatonservice.model.JwtResponse;
import com.tus.authenticatonservice.repository.UserRepository;
import com.tus.authenticatonservice.view.LoginForm;
import com.tus.authenticatonservice.view.LoginOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtTokenUtil jwtUtility;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm)
	{
		try
		{
			LoginOutput loginOutput = new LoginOutput();
			authenticate(loginForm);
			UserEntity user=userRepository.findByEmail(loginForm.getUsername());
			UserDetails ud = new User(user.getEmail().toString(), user.getPassword().toString(), new ArrayList<>());
			String jwttoken=jwtUtility.GenerateToken(ud);
			loginOutput.setJwttoken(jwttoken);
			return ResponseEntity.status(200).body(loginOutput);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	public void authenticate(LoginForm loginForm) throws Exception
	{
		try
		{
			authManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(),loginForm.getPassword()));
		}
		catch(BadCredentialsException ex) {
			throw new Exception("Incorrect user name or password");
		}
	}
}
