package com.assignment.se.dto;

import com.assignment.se.entity.Authority;
import com.assignment.se.entity.Course;
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

	private Set<String> authorityDtoSet;

	private Set<Long> lectureList;


	public static UserDto from(UserAuth user) {
		if(user == null) return null;

		return UserDto.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.name(user.getName())
				.semester(user.getSemester())
				.gender(user.getGender())
				.dob(user.getDob())
				.phone_number(user.getPhone_number())
				.email(user.getEmail())
				.authorityDtoSet(user.getAuthorities().stream()
						.map(Authority::getAuthorityName)
						.collect(Collectors.toSet()))
				.lectureList(user.getLectureUsers().stream().map(LectureUser::getCourse).map(Course::getId).collect(Collectors.toSet()))
				.build();
	}
}
