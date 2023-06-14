package com.assignment.se.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {


	@Id
	@Column(name = "authority_name", length = 50)
	private String authorityName;

	@Override
	public String toString() {
		return authorityName;
	}
}