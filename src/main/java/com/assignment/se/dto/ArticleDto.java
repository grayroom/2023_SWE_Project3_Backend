package com.assignment.se.dto;

import com.assignment.se.entity.Article;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
	private Long id;
	private Long board_id;
	private Long user_id;

	private String name;
	private String content;
	private String type;

	public static ArticleDto from(Article article) {
		return ArticleDto.builder()
				.id(article.getId())
				.board_id(article.getBoard().getId())
				.user_id(article.getUser().getId())
				.name(article.getName())
				.content(article.getContent())
				.type(article.getType())
				.build();
	}

	public static List<ArticleDto> from(List<Article> articleList) {
		return articleList.stream().map(ArticleDto::from).toList();
	}
}
