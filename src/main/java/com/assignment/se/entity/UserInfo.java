package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "user_info")
public class UserInfo {
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserAuth user_id;

	private Long semester;
	private Long gender;
	private LocalDate dob;
	private String phone_number;
	private String email;
}
