package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "lecture_attendance")
@IdClass(LectureAttendance.class)
public class LectureAttendance implements Serializable {
	@Id
	@ManyToOne
	@JoinColumn(name = "lecture_id")
	private Lecture lecture_id;

	@Id
	@ManyToOne
	@JoinColumn(name = "student_id")
	private UserAuth student_id;

	private String attendance_type;
}