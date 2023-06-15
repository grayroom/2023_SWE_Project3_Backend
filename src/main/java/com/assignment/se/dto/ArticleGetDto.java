package com.assignment.se.dto;

import com.assignment.se.entity.Article;
import lombok.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleGetDto {
	private Long id;
	private Long parent_article_id;
	private Long board_id;
	private Long user_id;
	private String user_name;

	private String name;
	private String content;
	private String type;

	private LocalDateTime created_at;

	List<String> filePathList;


	public static ArticleGetDto from(Article article) {
		return ArticleGetDto.builder()
				.id(article.getId())
				.board_id(article.getBoard().getId())
				.user_id(article.getUser().getId())
				.user_name(article.getUser().getName())
				.name(article.getName())
				.content(article.getContent())
				.type(article.getType())
				.created_at(article.getCreated_at())
				.build();
	}
	public static ArticleGetDto from(Article article, List<String> filePathList) {
		return ArticleGetDto.builder()
				.id(article.getId())
				.parent_article_id(article.getParent_article() == null ? null : article.getParent_article().getId())
				.board_id(article.getBoard().getId())
				.user_id(article.getUser().getId())
				.user_name(article.getUser().getName())
				.name(article.getName())
				.content(article.getContent())
				.type(article.getType())
				.created_at(article.getCreated_at())
				.filePathList(filePathList)
				.build();
	}

	public static List<ArticleGetDto> from(List<Article> articleList) {
		return articleList.stream().map(ArticleGetDto::from).toList();
	}
}
