package com.assignment.se.entity;

import com.assignment.se.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "article")
	private Article article;

	@ManyToOne
	@JoinColumn(name = "user")
	private UserAuth user;

	private String content;

	@Column(name = "created_at")
	private LocalDateTime created_at;

	public Comment(CommentDto comment) {
		this.article = Article.builder().id(comment.getParent_article_id()).build();
		this.user = UserAuth.builder().id(comment.getUser_id()).build();
		this.content = comment.getContent();
	}

	@PrePersist
	public void prePersist() {
		this.created_at = LocalDateTime.now();
	}
}
