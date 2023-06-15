package com.assignment.se.dto;

import com.assignment.se.entity.Comment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	private Long id;
	private Long parent_article_id;
	private Long user_id;
	private String user_name;

	private String content;
	private String created_at;

	public static CommentDto from(Comment comment) {
		return CommentDto.builder()
				.id(comment.getId())
				.parent_article_id(comment.getArticle().getId())
				.user_id(comment.getUser().getId())
				.user_name(comment.getUser().getName())
				.content(comment.getContent())
				.created_at(comment.getCreated_at().toString())
				.build();
	}

	public static List<CommentDto> from(List<Comment> commentList) {
		return commentList.stream().map(CommentDto::from).toList();
	}
}
