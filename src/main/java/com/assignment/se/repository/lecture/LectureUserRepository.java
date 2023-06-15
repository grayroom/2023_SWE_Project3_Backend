package com.assignment.se.repository.lecture;

import com.assignment.se.entity.Course;
import com.assignment.se.entity.LectureUser;
import com.assignment.se.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureUserRepository extends JpaRepository<LectureUser, Long> {
//	@Query("SELECT c FROM LectureUser c WHERE c.userAuth.username = :username")
	List<LectureUser> findByUser_Username(String username);

	LectureUser findByCourseAndUser(Course course, UserAuth userAuth);

	List<LectureUser> findByUserAndSemester(UserAuth userAuth, Long semesterId);
}
