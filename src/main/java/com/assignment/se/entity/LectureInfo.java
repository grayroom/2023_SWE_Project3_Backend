package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "lecture_info")
public class LectureInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lecturer_id")
	private UserAuth lecturer_id;

	private String name;
	private Long semester;
	private Long credit;
}
