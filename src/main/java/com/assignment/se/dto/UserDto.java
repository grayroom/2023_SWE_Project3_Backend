package com.assignment.se.dto;

import com.assignment.se.entity.LectureUser;
import com.assignment.se.entity.UserAuth;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long userId;

	private String username;

	private String password;

	private String name;

	private Long semester;

	private Long gender;

	private LocalDate dob;

	private String phone_number;

	private String email;

	private Set<AuthorityDto> authorityDtoSet;

	private Set<LectureUser> lectureList;


	public static UserDto from(UserAuth user) {
		if(user == null) return null;

		return UserDto.builder()
				.username(user.getUsername())
				.name(user.getName())
				.semester(user.getSemester())
				.gender(user.getGender())
				.dob(user.getDob())
				.phone_number(user.getPhone_number())
				.email(user.getEmail())
				.authorityDtoSet(user.getAuthorities().stream()
						.map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
						.collect(Collectors.toSet()))
				.lectureList(user.getLectureUsers())
				.build();
	}
}
