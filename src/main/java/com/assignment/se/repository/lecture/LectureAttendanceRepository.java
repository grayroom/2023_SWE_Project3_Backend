package com.assignment.se.repository.lecture;

import com.assignment.se.entity.LectureAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureAttendanceRepository extends JpaRepository<LectureAttendance, Long> {
}
