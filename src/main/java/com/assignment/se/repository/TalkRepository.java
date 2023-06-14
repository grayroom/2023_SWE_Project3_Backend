package com.assignment.se.repository;

import com.assignment.se.entity.Course;
import com.assignment.se.entity.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalkRepository extends JpaRepository<Talk, Long> {

	List<Talk> findAllByCourse(Course course);
}
