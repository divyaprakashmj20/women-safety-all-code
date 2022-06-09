package com.tus.authenticatonservice.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.tus.authenticatonservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class JwtTokenUtil implements Serializable {

	@Autowired
	UserRepository userRepository;

	private String Secret_key = "SymmetricKey12345";

	public String extractusername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return (Date) extractClaims(token, Claims::getExpiration);
	}

	public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		System.out.println(token);
		return Jwts.parser().setSigningKey(Secret_key).parseClaimsJws(token).getBody();
	}

	private Boolean IsTokenexpired(String token) {
		return extractExpiration(token).before(new Date());

	}

	public String GenerateToken(UserDetails userdetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("username", userdetails.getUsername());
		claims.put("userid", userRepository.findByEmail(userdetails.getUsername()).getId());
//		claims.put("userid", userdetails.getUsername());
		return createToken(claims, userdetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, Secret_key).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		System.out.println("validating token");
		final String username = extractusername(token);
		return (username.equals(userDetails.getUsername()) && !IsTokenexpired(token));
	}
}
