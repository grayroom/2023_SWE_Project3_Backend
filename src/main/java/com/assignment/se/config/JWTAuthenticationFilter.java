package com.assignment.se.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private Base64.Decoder decoder = Base64.getDecoder();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Get the Authorization header from the request
		String authorizationHeader = request.getHeader("Authorization");

		// Validate the Authorization header
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(7); // "Bearer "를 잘라낼 뿐

		// Validate the token
		try {
			// Replace this with your own JWT validation logic
			// This could involve verifying the signature and claims in the token
			boolean validToken = validateToken(token);

			if (!validToken) {
				throw new ServletException("Invalid token");
			}

			// Get the user details from the token and create an Authentication object
			UserDetails userDetails = getUserDetailsFromToken(token);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

			// Set the authentication object in the SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			throw new ServletException("Failed to authenticate user", e);
		}

		// Continue with the filter chain
		filterChain.doFilter(request, response);
	}

	// These methods need to be implemented
	private boolean validateToken(String token) {
		// parse token and validate signature
		final String[] splitJwt = token.split("\\.");
		final String headerStr = new String(decoder.decode(splitJwt[0].getBytes()));
		final String payloadStr = new String(decoder.decode(splitJwt[1].getBytes()));

		return true;
	}

	private UserDetails getUserDetailsFromToken(String token) {
		// ...
		return null;
	}
}
