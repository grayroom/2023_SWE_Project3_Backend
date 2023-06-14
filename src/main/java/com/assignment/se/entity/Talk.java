package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "talk")
public class Talk {
	@Id
	@Column(name = "talk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course_id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserAuth user_id;

	private String content;
	private String created_at;
}
