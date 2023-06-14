package com.assignment.se.repository.lecture;

import com.assignment.se.entity.Course;
import com.assignment.se.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
	List<Lecture> findAllByCourse(Course course);
}