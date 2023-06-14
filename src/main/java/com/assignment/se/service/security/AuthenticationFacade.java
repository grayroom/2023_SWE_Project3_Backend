package com.assignment.se.service.security;

import com.assignment.se.dto.UserDto;
import com.assignment.se.entity.UserAuth;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
	public Authentication getAuthentication();

	public boolean isAuthenticated();

	public UserAuth getPrincipal();

	public Long getUserId();
}
