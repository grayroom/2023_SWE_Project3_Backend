package com.assignment.se.dto.lecture;

import com.assignment.se.entity.LectureUser;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureUserDto {
	Long lecture_user_id;
	Long course_id;
	Long user_id;
	Long grade;
	Long semester;
	Long score;

	public static LectureUserDto from(LectureUser lectureUser) {
		return LectureUserDto.builder()
				.lecture_user_id(lectureUser.getLecture_user_id())
				.course_id(lectureUser.getCourse().getId())
				.user_id(lectureUser.getUserAuth().getId())
				.grade(lectureUser.getGrade())
				.semester(lectureUser.getSemester())
				.score(lectureUser.getScore())
				.build();
	}
}
