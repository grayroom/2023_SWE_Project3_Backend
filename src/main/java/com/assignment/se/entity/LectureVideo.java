package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lecture_video")
public class LectureVideo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;

	private String lecture_name;
	private String video_url;

	public LectureVideo(Lecture lecture, String video_url) {
		this.lecture = lecture;
		this.lecture_name = lecture.getLecture_name();
		this.video_url = video_url;
	}
}
