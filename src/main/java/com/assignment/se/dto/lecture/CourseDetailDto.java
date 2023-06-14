package com.assignment.se.dto.lecture;

import com.assignment.se.entity.CourseDetail;
import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailDto {
	Long id;
	Long course_id;

	String classroom;
	private String day_of_week;
	private Time begin_at;
	private Time end_at;

	public static CourseDetailDto from(CourseDetail courseDetail) {
		return CourseDetailDto.builder()
				.id(courseDetail.getId())
				.course_id(courseDetail.getCourse().getId())
				.classroom(courseDetail.getClassroom())
				.day_of_week(courseDetail.getDay_of_week())
				.begin_at(courseDetail.getBegin_at())
				.end_at(courseDetail.getEnd_at())
				.build();
	}

	public static List<CourseDetailDto> from(List<CourseDetail> courseDetail) {
		return courseDetail.stream().map(CourseDetailDto::from).toList();
	}
}
