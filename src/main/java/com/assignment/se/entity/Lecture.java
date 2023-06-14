package com.assignment.se.entity;

import com.assignment.se.dto.lecture.LectureDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lecture")
public class Lecture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lecture_id")
	private Course course_id;

	private String classroom;
	private String lecture_name;
	private LocalDateTime begin_at;
	private LocalDateTime end_at;


	public Lecture (LectureDto lecture, Course course) {
		this.course_id = course;
		this.classroom = lecture.getClassroom();
		this.lecture_name = lecture.getLecture_name();
		this.begin_at = lecture.getBegin_at();
		this.end_at = lecture.getEnd_at();
	}
}
