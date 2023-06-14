package com.assignment.se.dto;

import com.assignment.se.entity.Talk;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalkDto {
	Long id;
	Long course_id;

	String content;
	LocalDateTime created_at;

	public static TalkDto from(Talk talk) {
		return TalkDto.builder()
				.id(talk.getId())
				.course_id(talk.getCourse().getId())
				.content(talk.getContent())
				.created_at(talk.getCreated_at())
				.build();
	}

	public static List<TalkDto> from(List<Talk> talkList) {
		return talkList.stream().map(TalkDto::from).toList();
	}
}
