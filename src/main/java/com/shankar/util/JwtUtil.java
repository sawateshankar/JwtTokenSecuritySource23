package com.shankar.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private String SECRET_KEY = "secret";

	
	public String extarctUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	//validate Token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extarctUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
	//CheckTokenExpiry
	private Boolean isTokenExpired(String token) {
		return extarctExpiration(token).before(new Date());
	}
	public Date extarctExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	//create Claims
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
	
	//create token
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}
	
}
