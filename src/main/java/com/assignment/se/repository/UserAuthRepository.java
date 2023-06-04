package com.assignment.se.repository;

import com.assignment.se.entity.UserAuth;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
	@EntityGraph(attributePaths = "authorities")
	Optional<UserAuth> findOneWithAuthoritiesByUsername(String username);
}
