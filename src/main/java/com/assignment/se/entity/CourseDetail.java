package com.assignment.se.entity;

import com.assignment.se.dto.lecture.CourseDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
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
	@JoinColumn(name = "course")
	private Course course;

	private String classroom;

	private String day_of_week;
	private Time begin_at;
	private Time end_at;

	public CourseDetail(CourseDetailDto courseDetailDto, Course course) {
		this.course = course;
		this.classroom = courseDetailDto.getClassroom();
		this.day_of_week = courseDetailDto.getDay_of_week();
		this.begin_at = courseDetailDto.getBegin_at();
		this.end_at = courseDetailDto.getEnd_at();
	}
}
