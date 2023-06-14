package com.assignment.se.entity;

import com.assignment.se.dto.lecture.CourseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {
	@Id
	@Column(name = "lecture_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lecturer_id")
	private UserAuth lecturer_id;

	private String name;
	private Long semester;
	private Long credit; // 학점수
	private String type; // 전공/교양 구분

	public Course(CourseDto courseDto, UserAuth lecturer) {
		this.lecturer_id = lecturer;
		this.name = courseDto.getName();
		this.semester = courseDto.getSemester();
		this.credit = courseDto.getCredit();
		this.type = courseDto.getType();
	}

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Set<LectureUser> lectureUsers = new HashSet<LectureUser>(0);
}
