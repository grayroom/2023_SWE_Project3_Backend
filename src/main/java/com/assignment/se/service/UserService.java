package com.assignment.se.service;

import java.util.Collections;

import com.assignment.se.dto.UserDto;
import com.assignment.se.entity.Authority;
import com.assignment.se.entity.UserAuth;
import com.assignment.se.exception.DuplicateMemberException;
import com.assignment.se.exception.NotFoundMemberException;
import com.assignment.se.repository.UserAuthRepository;
import com.assignment.se.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private final UserAuthRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserAuthRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserDto signup(UserDto userDto) {
		if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
			throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
		}

		Authority authority = Authority.builder()
				.authorityName("ROLE_USER")
				.build();

		UserAuth user = UserAuth.builder()
				.username(userDto.getUsername())
				.name(userDto.getName())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.authorities(Collections.singleton(authority))
				.activated(true)
				.build();

		return UserDto.from(userRepository.save(user));
	}

	@Transactional(readOnly = true)
	public UserDto getUserWithAuthorities(String username) {
		return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
	}

	@Transactional(readOnly = true)
	public UserDto getMyUserWithAuthorities() {
		return UserDto.from(
				SecurityUtil.getCurrentUsername()
						.flatMap(userRepository::findOneWithAuthoritiesByUsername)
						.orElseThrow(() -> new NotFoundMemberException("Member not found"))
		);
	}
}
