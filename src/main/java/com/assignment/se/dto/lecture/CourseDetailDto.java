package com.assignment.se.dto.lecture;

import com.assignment.se.entity.CourseDetail;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailDto {
	Long id;
	Long course_id;

	String classroom;
	LocalDateTime begin_at;
	LocalDateTime end_at;

	public static CourseDetailDto from(CourseDetail courseDetail) {
		return CourseDetailDto.builder()
				.id(courseDetail.getId())
				.course_id(courseDetail.getCourse().getId())
				.classroom(courseDetail.getClassroom())
				.begin_at(courseDetail.getBegin_at())
				.end_at(courseDetail.getEnd_at())
				.build();
	}
}
