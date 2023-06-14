package com.assignment.se.dto.lecture;

import com.assignment.se.entity.Course;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
	private Long courseId;

	private Long lecturer_id;

	private String name;
	private String description;

	private Long semester;
	private Long credit;
	private String type;

	public static CourseDto from(Course course) {
		return CourseDto.builder()
				.lecturer_id(course.getLecturer_id().getId())
				.courseId(course.getId())
				.name(course.getName())
				.semester(course.getSemester())
				.credit(course.getCredit())
				.type(course.getType())
				.build();
	}
}
