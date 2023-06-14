package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "talk")
public class Talk {
	@Id
	@Column(name = "talk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "course")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserAuth user_id;

	@Column(name = "content")
	private String content;

	@GeneratedValue(strategy = GenerationType.AUTO)
	private LocalDateTime created_at;

	@PrePersist
	public void prePersist() {
		this.created_at = LocalDateTime.now();
	}
}
