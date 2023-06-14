package com.assignment.se.repository.lecture;

import com.assignment.se.entity.LectureUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureUserRepository extends JpaRepository<LectureUser, Long> {
//	@Query("SELECT c FROM LectureUser c WHERE c.userAuth.username = :username")
	List<LectureUser> findByUserAuth_Username(String username);
}
