package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lecture_user")
public class LectureUser {
	@Id
	@Column(name = "lecture_user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lecture_user_id;

	@ManyToOne
	@JoinColumn(name = "course")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "user")
	private UserAuth user;

	private Long grade;
	private Long semester;
	private Long score;
}
