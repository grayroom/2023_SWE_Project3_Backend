package com.assignment.se.dto.lecture;

import com.assignment.se.entity.Lecture;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto {
	Long id;
	Long course_id;

	String classroom;
	String lecture_name;
	LocalDateTime begin_at;
	LocalDateTime end_at;

	public static LectureDto from(Lecture lecture) {
		return LectureDto.builder()
				.id(lecture.getId())
				.course_id(lecture.getCourse().getId())
				.classroom(lecture.getClassroom())
				.lecture_name(lecture.getLecture_name())
				.begin_at(lecture.getBegin_at())
				.end_at(lecture.getEnd_at())
				.build();
	}

	public static List<LectureDto> from(List<Lecture> lectureList) {
		return lectureList.stream().map(LectureDto::from).toList();
	}
}
