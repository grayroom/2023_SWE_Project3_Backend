package com.assignment.se.dto.lecture;

import com.assignment.se.entity.LectureVideo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureVideoDto {
	Long id;
	Long lecture;

	String lecture_name;
	String video_url;

	public static LectureVideoDto from(LectureVideo lectureVideo) {
		return LectureVideoDto.builder()
				.lecture(lectureVideo.getLecture().getId())
				.id(lectureVideo.getId())
				.lecture_name(lectureVideo.getLecture_name())
				.video_url(lectureVideo.getVideo_url())
				.build();
	}

	public static List<LectureVideoDto> from(List<LectureVideo> lectureVideo) {
		return lectureVideo.stream().map(LectureVideoDto::from).toList();
	}
}
