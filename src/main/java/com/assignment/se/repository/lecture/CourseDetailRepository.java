package com.assignment.se.repository.lecture;

import com.assignment.se.entity.Course;
import com.assignment.se.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {
	@Query("SELECT cd FROM CourseDetail cd WHERE cd.course = ?1")
	List<CourseDetail> findAllByCourse(Course course);
}
