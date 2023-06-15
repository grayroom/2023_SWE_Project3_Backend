package com.assignment.se.dto;

import com.assignment.se.entity.Article;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
	private String user_name;

	private String name;
	private String content;
	private String type;

	private LocalDateTime created_at;

	List<MultipartFile> files;

	public static ArticleDto from(Article article) {
		return ArticleDto.builder()
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

	public static List<ArticleDto> from(List<Article> articleList) {
		return articleList.stream().map(ArticleDto::from).toList();
	}
}
