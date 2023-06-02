package com.assignment.se.entity;

import com.assignment.se.entity.domain.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자의 식별을 위한 id, 사용자의 이름, 사용자의 비밀번호, 사용자의 권한을 저장하는 entity입니다
 */
@Entity
@Getter
@Setter
@Table(name = "user_auth")
public class UserAuth {
	@Id
	private String id;
	private String name;
	private String password;
	private Authority authority;
}
