package com.assignment.se.entity;

import com.assignment.se.dto.lecture.CourseDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_detail")
public class CourseDetail {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	private String classroom;
	private LocalDateTime begin_at;
	private LocalDateTime end_at;

	public CourseDetail(CourseDetailDto courseDetailDto, Course course) {
		this.course = course;
		this.classroom = courseDetailDto.getClassroom();
		this.begin_at = courseDetailDto.getBegin_at();
		this.end_at = courseDetailDto.getEnd_at();
	}
}
