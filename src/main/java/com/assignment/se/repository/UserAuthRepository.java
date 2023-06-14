package com.assignment.se.repository;

import com.assignment.se.dto.UserDto;
import com.assignment.se.entity.UserAuth;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
	@EntityGraph(attributePaths = "authorities")
	Optional<UserAuth> findOneWithAuthoritiesByUsername(String username);

	Optional<UserAuth> findByUsername(String name);
}
