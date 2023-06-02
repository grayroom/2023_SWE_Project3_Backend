package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "lecture_video")
public class LectureVideo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lecture__id")
	private Lecture lecture__id;

	private String lecture_name;
	private String video_url;
}
