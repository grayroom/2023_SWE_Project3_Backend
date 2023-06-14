package com.assignment.se.dto;

import com.assignment.se.entity.Board;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
	private Long id;
	private Long course_id;
	private String name;

	public static BoardDto from(Board board) {
		return BoardDto.builder()
				.id(board.getId())
				.course_id(board.getCourse().getId())
				.name(board.getName())
				.build();
	}

	public static List<BoardDto> from(List<Board> boards) {
		return boards.stream().map(BoardDto::from).toList();
	}
}
