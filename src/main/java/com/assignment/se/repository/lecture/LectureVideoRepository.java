package com.assignment.se.repository.lecture;

import com.assignment.se.entity.Lecture;
import com.assignment.se.entity.LectureVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureVideoRepository extends JpaRepository<LectureVideo, Long> {
	List<LectureVideo> findByLecture(Lecture lecture);
}
