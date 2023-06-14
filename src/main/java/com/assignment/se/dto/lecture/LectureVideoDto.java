package com.assignment.se.dto.lecture;

import com.assignment.se.entity.LectureVideo;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureVideoDto {
	Long id;
	Long lecture_id;

	String lecture_name;
	String video_url;

	public static LectureVideoDto from(LectureVideo lectureVideo) {
		return LectureVideoDto.builder()
				.lecture_id(lectureVideo.getLecture_id().getId())
				.id(lectureVideo.getId())
				.lecture_name(lectureVideo.getLecture_name())
				.video_url(lectureVideo.getVideo_url())
				.build();
	}
}
