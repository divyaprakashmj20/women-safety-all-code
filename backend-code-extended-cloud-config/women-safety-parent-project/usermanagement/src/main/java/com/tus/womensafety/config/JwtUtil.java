package com.tus.womensafety.config;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil implements Serializable {

	private String Secret_key = "SymmetricKey12345";

	public String extractusername(String token) {
		Claims claimMap= Jwts.parser().setSigningKey(Secret_key).parseClaimsJws(token).getBody();
		return claimMap.get("username").toString();
	}
}