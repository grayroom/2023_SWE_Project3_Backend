package com.assignment.se.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth")
public class UserAuth  {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", length = 50, unique = true)
	private String username;

	private String name;
	private String password;

	private Long semester;
	private Long gender;
	private LocalDate dob;
	private String phone_number;
	private String email;

	private boolean activated;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Authority> authorities;

	@OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
	private Set<LectureUser> lectureUsers = new HashSet<LectureUser>(0);

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return activated;
	}

//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return (Collection<? extends GrantedAuthority>) this.authorities;
//	}
}
