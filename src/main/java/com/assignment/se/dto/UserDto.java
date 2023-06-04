package com.assignment.se.dto;

import com.assignment.se.entity.UserAuth;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private String username;

	private String password;

	private String name;

	private Set<AuthorityDto> authorityDtoSet;

	public static UserDto from(UserAuth user) {
		if(user == null) return null;

		return UserDto.builder()
				.username(user.getUsername())
				.authorityDtoSet(user.getAuthorities().stream()
						.map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
						.collect(Collectors.toSet()))
				.build();
	}

}
