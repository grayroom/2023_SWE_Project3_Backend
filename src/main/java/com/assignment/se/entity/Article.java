package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "article")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board_id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserAuth user_id;

	private String name;

	@Column(length = 8192)
	private String content;
	private String type;
}
