package com.tus.authenticatonservice.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticateEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = 1101213356031684043L;

	
	public void commence(HttpServletRequest request, HttpServletResponse response,
	AuthenticationException authException) throws IOException {
	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
