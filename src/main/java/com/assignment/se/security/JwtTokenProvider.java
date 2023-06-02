package com.assignment.se.security;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

//	@Value("${spring.security.jwt.secret}")
	private String secretKey;

//	@Value("${spring.security.jwt.expiration}")
	private long expirationMs;

	public String GenerateToken(Authentication authentication) {
		return "";
	}

}
