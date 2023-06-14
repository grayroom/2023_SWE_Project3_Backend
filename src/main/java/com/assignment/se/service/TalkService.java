package com.assignment.se.service;

import com.assignment.se.dto.TalkDto;
import com.assignment.se.dto.lecture.CourseDto;
import com.assignment.se.entity.Course;
import com.assignment.se.entity.Talk;
import com.assignment.se.repository.TalkRepository;
import com.assignment.se.repository.lecture.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalkService {
	private final TalkRepository talkRepository;
	private final CourseRepository courseRepository;

	@Autowired
	public TalkService(TalkRepository talkRepository, CourseRepository courseRepository) {
		this.talkRepository = talkRepository;
		this.courseRepository = courseRepository;
	}

	public List<TalkDto> getTalkList(CourseDto courseDto) {
		Course course = courseRepository.findById(courseDto.getCourse_id()).orElseThrow();
		List<Talk> talkList = talkRepository.findAllByCourse(course);
		return TalkDto.from(talkList);
	}

	public TalkDto createTalk(TalkDto talkDto) {
		Course course = courseRepository.findById(talkDto.getCourse_id()).orElseThrow();
		Talk talk = Talk.builder()
				.course(course)
				.content(talkDto.getContent())
				.build();
		return TalkDto.from(talkRepository.save(talk));
	}
}
