package com.assignment.se.service;

import com.assignment.se.dto.lecture.LectureDto;
import com.assignment.se.dto.lecture.CourseDto;
import com.assignment.se.dto.lecture.LectureVideoDto;
import com.assignment.se.entity.*;
import com.assignment.se.repository.lecture.CourseRepository;
import com.assignment.se.repository.lecture.LectureRepository;
import com.assignment.se.repository.lecture.LectureUserRepository;
import com.assignment.se.repository.lecture.LectureVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class LectureService {

	private final UserService userService;
	private final CourseRepository courseRepository;
	private final LectureRepository lectureRepository;
	private final LectureUserRepository lectureUserRepository;
	private final LectureVideoRepository lectureVideoRepository;

	@Value("${lecture.file.path}")
	private String LECTURE_FILE_PATH;

	@Autowired
	public LectureService(UserService userService, CourseRepository courseRepository,
	                      LectureRepository lectureRepository, LectureUserRepository lectureUserRepository,
	                      LectureVideoRepository lectureVideoRepository) {
		this.userService = userService;
		this.courseRepository = courseRepository;
		this.lectureRepository = lectureRepository;
		this.lectureUserRepository = lectureUserRepository;
		this.lectureVideoRepository = lectureVideoRepository;
	}

	public List<Course> getLectureList() {
		return courseRepository.findAll();
	}

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
}
