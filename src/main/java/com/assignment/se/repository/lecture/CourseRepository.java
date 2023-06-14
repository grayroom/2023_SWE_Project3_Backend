package com.assignment.se.repository.lecture;

import com.assignment.se.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
