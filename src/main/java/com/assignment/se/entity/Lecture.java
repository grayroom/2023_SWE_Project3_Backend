package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "lecture")
public class Lecture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lecture_info_id")
	private LectureInfo lecture_info_id;

	private String classroom;
	private String lecture_name;
	private LocalDateTime begin_at;
	private LocalDateTime end_at;
}
