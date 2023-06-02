package com.assignment.se.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
	@Value("${jwt.issuer}")
	private String issuer;

	@Value("${jwt.secret}")
	private String secret;

	public String create(final int userId) {
		try {
			JWTCreator.Builder builder = JWT.create()
					.withIssuer(issuer)
					.withClaim("userId", userId)
					.withExpiresAt(expiresAt());
			return builder.sign(Algorithm.HMAC256(secret));
		} catch (JWTCreationException jwtCreationException) {
			log.info("Error creating JWT token", jwtCreationException);
		}
		return null;
	}

	private Date expiresAt()  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, 744); // 24 * 31
		return cal.getTime();
	}

	public static class TokenRes {
		private String token;

		public TokenRes() {

		}

		public TokenRes(final String token) {
			this.token = token;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}
}
