package com.assignment.se.dto;

import com.assignment.se.dto.lecture.CourseDto;
import com.assignment.se.entity.Course;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSemesterDto {
	List<CourseDto> courseList;
	Long totalCredit;

	public static UserSemesterDto from(List<Course> courseList) {
		return UserSemesterDto.builder()
				.courseList(courseList.stream().map(CourseDto::from).toList())
				.totalCredit(courseList.stream().mapToLong(Course::getCredit).sum())
				.build();
	}
}
