package com.assignment.se.service.security;

import com.assignment.se.dto.UserDto;
import com.assignment.se.entity.UserAuth;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {

	private static AuthenticationTrustResolver authTrustResolver = new AuthenticationTrustResolverImpl();
	@Override
	public Authentication getAuthentication() {
	    return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public boolean isAuthenticated() {
		return !authTrustResolver.isAnonymous(getAuthentication());
	}

	private boolean isAuthenticated(Authentication authentication) {
		return !authTrustResolver.isAnonymous(authentication);
	}

	@Override
	public UserAuth getPrincipal() {
		Authentication authentication = getAuthentication();
		if (!isAuthenticated(authentication))
			throw new IllegalStateException("Authentication is not authenticated");

		return (UserAuth) authentication.getPrincipal();
	}

	@Override
	public Long getUserId() {
		return getPrincipal().getId();
	}
}
