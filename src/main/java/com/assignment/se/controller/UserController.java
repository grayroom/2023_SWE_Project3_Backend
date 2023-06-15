package com.assignment.se.controller;

import com.assignment.se.dto.UserDto;
import com.assignment.se.entity.UserAuth;
import com.assignment.se.repository.UserAuthRepository;
import com.assignment.se.service.UserService;
import com.assignment.se.service.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;
	private final AuthenticationFacade authenticationFacade;
	private final UserAuthRepository userAuthRepository;

	@Autowired
	public UserController(UserService userService, AuthenticationFacade authenticationFacade,
	                      UserAuthRepository userAuthRepository) {
		this.userService = userService;
		this.authenticationFacade = authenticationFacade;
		this.userAuthRepository = userAuthRepository;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserDto> signup(
			@RequestBody UserDto userDto
	) {
		return ResponseEntity.ok(userService.signup(userDto));
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
		// get user information
		Authentication authentication = authenticationFacade.getAuthentication();
		Optional<UserAuth> optionalUserAuth = userAuthRepository.findByUsername(authentication.getName());
		if (optionalUserAuth.isPresent()) {
			UserAuth userAuth = optionalUserAuth.get();
			return ResponseEntity.ok(UserDto.from(userAuth));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username));
	}

	@GetMapping("/user/list")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<UserDto>> getUserList() {
		return ResponseEntity.ok().body(userService.getUserList());
	}

}
