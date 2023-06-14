package com.assignment.se.controller;

import com.assignment.se.dto.UserSemesterDto;
import com.assignment.se.dto.lecture.LectureDto;
import com.assignment.se.dto.lecture.CourseDto;
import com.assignment.se.dto.lecture.LectureVideoDto;
import com.assignment.se.entity.Course;
import com.assignment.se.entity.LectureUser;
import com.assignment.se.entity.UserAuth;
import com.assignment.se.repository.UserAuthRepository;
import com.assignment.se.service.LectureService;
import com.assignment.se.service.security.AuthenticationFacade;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/lecture")
public class LectureController {
	private final LectureService lectureService;
	private final UserAuthRepository userAuthRepository;
	private final AuthenticationFacade authenticationFacade;

	@Autowired
	public LectureController(LectureService lectureService, UserAuthRepository userAuthRepository, AuthenticationFacade authenticationFacade) {
		this.lectureService = lectureService;
		this.userAuthRepository = userAuthRepository;
		this.authenticationFacade = authenticationFacade;
	}

	@GetMapping("/list")
	public ResponseEntity<List<CourseDto>> getLectureList() {
		List<Course> lectureList = lectureService.getLectureList();
		return ResponseEntity.ok(lectureList.stream().map(CourseDto::from).toList());
	}

	@GetMapping("/user-list")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<UserSemesterDto> getMyLectureInfo(HttpServletRequest request) {
		// get user information
		Authentication authentication = authenticationFacade.getAuthentication();
		Optional<UserAuth> optionalUserAuth = userAuthRepository.findByUsername(authentication.getName());
		if (optionalUserAuth.isPresent()) {
			UserAuth userAuth = optionalUserAuth.get();

			return ResponseEntity.ok(UserSemesterDto.from(
					lectureService.getUserLectureList(userAuth.getUsername())
			));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/apply")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<LectureUser> applyLecture(@RequestBody Map<String, Long> param) {
		Authentication authentication = authenticationFacade.getAuthentication();
		Optional<UserAuth> optionalUserAuth = userAuthRepository.findByUsername(authentication.getName());
		if (optionalUserAuth.isPresent()) {
			UserAuth userAuth = optionalUserAuth.get();
			LectureUser lectureUser = lectureService.applyLecture(userAuth, param.get("lectureId"));
			return ResponseEntity.ok(lectureUser);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/add-course")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
//	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CourseDto> createLectureInfo(@RequestBody CourseDto lecture) {
		CourseDto lectureInfo = lectureService.createLecture(lecture);
		return ResponseEntity.ok(lectureInfo);
	}

	@PostMapping("/add-lecture")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<LectureDto> addLecture(@RequestBody LectureDto lecture) {
		LectureDto lectureInfo = lectureService.addLecture(lecture);
		return ResponseEntity.ok(lectureInfo);
	}

	@PostMapping(value = "/add-video", consumes = {"multipart/form-data"})
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<LectureVideoDto> addVideo(
			@ModelAttribute("lecture") Long lecture_id, @RequestPart("video") MultipartFile video
	) throws Exception {
		if (lecture_id == null || video == null) {
			return ResponseEntity.badRequest().build();
		}
		LectureVideoDto lectureVideo = lectureService.addVideo(lecture_id, video);
		return ResponseEntity.ok(lectureVideo);
	}
}