package com.assignment.se.service;

import com.assignment.se.dto.lecture.CourseDetailDto;
import com.assignment.se.dto.lecture.LectureDto;
import com.assignment.se.dto.lecture.CourseDto;
import com.assignment.se.dto.lecture.LectureVideoDto;
import com.assignment.se.entity.*;
import com.assignment.se.repository.lecture.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class LectureService {

	private final UserService userService;
	private final CourseRepository courseRepository;
	private final LectureRepository lectureRepository;
	private final LectureUserRepository lectureUserRepository;
	private final LectureVideoRepository lectureVideoRepository;
	private final LectureAttendanceRepository lectureAttendanceRepository;

	private final CourseDetailRepository courseDetailRepository;

	@Value("${lecture.file.path}")
	private String LECTURE_FILE_PATH;

	@Autowired
	public LectureService(UserService userService, CourseRepository courseRepository,
	                      LectureRepository lectureRepository, LectureUserRepository lectureUserRepository,
	                      LectureVideoRepository lectureVideoRepository, LectureAttendanceRepository lectureAttendanceRepository,
	                      CourseDetailRepository courseDetailRepository) {
		this.userService = userService;
		this.courseRepository = courseRepository;
		this.lectureRepository = lectureRepository;
		this.lectureUserRepository = lectureUserRepository;
		this.lectureVideoRepository = lectureVideoRepository;
		this.lectureAttendanceRepository = lectureAttendanceRepository;
		this.courseDetailRepository = courseDetailRepository;
	}

	public List<Course> getLectureList() {
		return courseRepository.findAll();
	}

	// 특정 유저가 듣는 강의 목록을 가져온다.
	public List<Course> getUserLectureList(String username) {
		List<LectureUser> lectureUserList = lectureUserRepository.findByUserAuth_Username(username);
		// extract only lectureInfo from lectureUserList
		return lectureUserList.stream().map(LectureUser::getCourse).toList();
	}

	public LectureUser applyLecture(UserAuth userAuth, Long lectureId) {
		Course course = courseRepository.findById(lectureId).orElseThrow();
		LectureUser lectureUser = new LectureUser();
		lectureUser.setUserAuth(userAuth);
		lectureUser.setCourse(course);
		return lectureUserRepository.save(lectureUser);
	}

	public CourseDto createLecture(CourseDto courseDto) {
		// query Userauth from userDto
		try {
			// get usreauth by userid
			UserAuth userAuth = userService.getUserAuth(courseDto.getLecturer_id());
			Course course = new Course(courseDto, userAuth);
			return CourseDto.from(courseRepository.save(course));
		} catch (Exception e) {
			throw new RuntimeException("UserAuth(lecturer) not found");
		}
	}

	public LectureDto addLecture(LectureDto lecture) {
		Course course = courseRepository.findById(lecture.getCourse_id()).orElseThrow();
		Lecture newLecture = new Lecture(lecture, course);
		return LectureDto.from(lectureRepository.save(newLecture));
	}

	public LectureVideoDto addVideo(Long lecture_id, MultipartFile video) throws Exception {
		try {
			Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow();
			Path filePath = Path.of(LECTURE_FILE_PATH, lecture.getId().toString(), video.getOriginalFilename());
			// make file first
			Files.createDirectories(filePath.getParent());
			// save file
			Files.copy(video.getInputStream(), filePath);
			LectureVideo lectureVideo = new LectureVideo(lecture, filePath.toString());

			return LectureVideoDto.from(lectureVideoRepository.save(lectureVideo));
		} catch(Exception e) {
			throw new Exception("Failed to save lecture video");
		}
	}

	public List<LectureVideoDto> getLectureVideoList(Long lecture_id) {
		Lecture lecture = lectureRepository.findById(lecture_id).orElseThrow();
		// lecture_id가 lecture의 id와 같은 lectureVideo를 가져온다.
		List<LectureVideo> lectureVideoList = lectureVideoRepository.findByLecture(lecture);
		return LectureVideoDto.from(lectureVideoList);
	}

	public LectureAttendance attendLecture(Long lectureId, UserAuth userAuth) {
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow();
		LectureAttendance lectureAttendance = new LectureAttendance(lecture, userAuth, "출석");
		return lectureAttendanceRepository.save(lectureAttendance);
	}

	public CourseDetailDto createLectureDetail(CourseDetailDto lecture) {
		Course course = courseRepository.findById(lecture.getCourse_id()).orElseThrow();
		CourseDetail newLecture = new CourseDetail(lecture, course);
		return CourseDetailDto.from(courseDetailRepository.save(newLecture));
	}
}
