package com.assignment.se.entity;

import com.assignment.se.entity.domain.Day;
import com.assignment.se.entity.domain.Period;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lecture_day")
public class LectureDay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lecture_info_id")
	private LectureInfo lecture_info_id;

	private Day day;
	private Period period;
}
