package com.assignment.se.service;

import com.assignment.se.dto.BoardDto;
import com.assignment.se.dto.lecture.*;
import com.assignment.se.entity.*;
import com.assignment.se.repository.BoardRepository;
import com.assignment.se.repository.lecture.*;
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
	private final LectureAttendanceRepository lectureAttendanceRepository;
	private final BoardRepository boardRepository;

	private final CourseDetailRepository courseDetailRepository;

	@Value("${lecture.file.path}")
	private String LECTURE_FILE_PATH;

	@Autowired
	public LectureService(UserService userService, CourseRepository courseRepository,
	                      LectureRepository lectureRepository, LectureUserRepository lectureUserRepository,
	                      LectureVideoRepository lectureVideoRepository, LectureAttendanceRepository lectureAttendanceRepository,
	                      CourseDetailRepository courseDetailRepository, BoardRepository boardRepository) {
		this.userService = userService;
		this.courseRepository = courseRepository;
		this.lectureRepository = lectureRepository;
		this.lectureUserRepository = lectureUserRepository;
		this.lectureVideoRepository = lectureVideoRepository;
		this.lectureAttendanceRepository = lectureAttendanceRepository;
		this.courseDetailRepository = courseDetailRepository;
		this.boardRepository = boardRepository;
	}

	public List<Course> getCourseList() {
		return courseRepository.findAll();
	}

	// 특정 유저가 듣는 강의 목록을 가져온다.
	public List<Course> getUserLectureList(String username) {
		List<LectureUser> lectureUserList = lectureUserRepository.findByUser_Username(username);
		// extract only lectureInfo from lectureUserList
		return lectureUserList.stream().map(LectureUser::getCourse).toList();
	}

	public LectureUser applyLecture(UserAuth userAuth, Long lectureId) {
		Course course = courseRepository.findById(lectureId).orElseThrow();
		LectureUser lectureUser = new LectureUser();
		lectureUser.setUser(userAuth);
		lectureUser.setCourse(course);
		return lectureUserRepository.save(lectureUser);
	}

	public CourseDto createCourse(CourseDto courseDto) {
		try {
			UserAuth userAuth = userService.getUserAuth(courseDto.getLecturer_id());
			Course course = new Course(courseDto, userAuth);
			CourseDto resp = CourseDto.from(courseRepository.save(course));

			for(String subject : List.of("공지", "강의자료", "과제", "질문")) {
				Board board = new Board();
				board.setCourse(course);
				board.setName(course.getName() + ": " + subject);
				boardRepository.save(board);
			}

			return resp;
		} catch (Exception e) {
			throw new RuntimeException("UserAuth(lecturer) not found");
		}
	}

	public List<Object> getCourseInfo(Long id) {
		Course targetCourse = courseRepository.findById(id).orElseThrow();
		List<Object> response = new java.util.ArrayList<>(List.of(CourseDto.from(targetCourse)));
		// append CourseDetailDto.from(courseDetailRepository.findAllByCourse(targetCourse))
		response.add(CourseDetailDto.from(courseDetailRepository.findAllByCourse(targetCourse)));
		response.add(BoardDto.from(boardRepository.findAllByCourse(targetCourse)));
		return response;
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
			LectureVideo lectureVideo = new LectureVideo(
					lecture,
					Path.of("/resource/lecture/video", lecture.getId().toString(), video.getOriginalFilename()).toString());

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

	public LectureDto getLectureInfo(Long id) {
		Lecture lecture = lectureRepository.findById(id).orElseThrow();
		return LectureDto.from(lecture);
	}

	public List<Object> getCourseLectureList(Long id) {
		Course course = courseRepository.findById(id).orElseThrow();
		List<Object> response = new java.util.ArrayList<>();
		response.add(LectureDto.from(lectureRepository.findAllByCourse(course)));
		return response;
	}

	public LectureUserDto gradeLecture(LectureUserDto lectureUserDto) {
		Course course = courseRepository.findById(lectureUserDto.getCourse_id()).orElseThrow();
		UserAuth userAuth = userService.getUserAuth(lectureUserDto.getUser_id());

		LectureUser lectureUser = lectureUserRepository.findByCourseAndUser(course, userAuth);
		lectureUser.setGrade(lectureUserDto.getGrade());
		lectureUser.setScore(lectureUserDto.getScore());
		lectureUser.setSemester(course.getSemester());
		return LectureUserDto.from(lectureUserRepository.save(lectureUser));
	}

	public List<LectureUserDto> getGrade(UserAuth userAuth, Long semesterId) {
		List<LectureUser> lectureUserList = lectureUserRepository.findByUserAndSemester(userAuth, semesterId);
		return LectureUserDto.from(lectureUserList);
	}
}
