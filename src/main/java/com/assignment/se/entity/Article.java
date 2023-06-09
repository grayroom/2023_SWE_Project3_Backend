package com.assignment.se.entity;

import com.assignment.se.dto.ArticleDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "parent_article")
	private Article parent_article;

	@ManyToOne
	@JoinColumn(name = "board")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user")
	private UserAuth user;

	private String name;

	@Column(length = 8192)
	private String content;
	private String type;

	@Column(name = "created_at")
	private LocalDateTime created_at;

	public Article(ArticleDto articleDto) {
		this.parent_article = articleDto.getParent_article_id() == null ? null : Article.builder().id(articleDto.getParent_article_id()).build();
		this.name = articleDto.getName();
		this.content = articleDto.getContent();
		this.type = articleDto.getType();
	}

	@PrePersist
	public void prePersist() {
		this.created_at = LocalDateTime.now();
	}
}
