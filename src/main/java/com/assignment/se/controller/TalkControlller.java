package com.assignment.se.controller;

import com.assignment.se.dto.TalkDto;
import com.assignment.se.dto.lecture.CourseDto;
import com.assignment.se.repository.lecture.CourseRepository;
import com.assignment.se.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/talk")
public class TalkControlller {

	private final TalkService talkService;

	@Autowired
	public TalkControlller(TalkService talkService) {
		this.talkService = talkService;
	}

	@PostMapping("/list")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<List<TalkDto>> getTalkList(@RequestBody CourseDto courseDto) {
		List<TalkDto> talkList = talkService.getTalkList(courseDto);
		return ResponseEntity.ok().body(talkList);
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<TalkDto> createTalk(@RequestBody TalkDto talkDto) {
		TalkDto createdTalk = talkService.createTalk(talkDto);
		return ResponseEntity.ok(createdTalk);
	}
}
