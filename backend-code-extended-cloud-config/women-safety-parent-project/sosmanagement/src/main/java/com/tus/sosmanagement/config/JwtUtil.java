package com.tus.sosmanagement.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class JwtUtil implements Serializable {

	private String Secret_key = "SymmetricKey12345";

	public String extractusername(String token) {
		Claims claimMap= Jwts.parser().setSigningKey(Secret_key).parseClaimsJws(token).getBody();
		return claimMap.get("username").toString();
	}

	public String extractUserId(String token){
		Claims claimMap= Jwts.parser().setSigningKey(Secret_key).parseClaimsJws(token).getBody();
		return claimMap.get("userid").toString();
	}

}