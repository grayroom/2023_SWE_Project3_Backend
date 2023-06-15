package com.assignment.se.controller;

import com.assignment.se.dto.CommentDto;
import com.assignment.se.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/comment")
public class CommentController {
	private final CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/add")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto comment) {
		return ResponseEntity.ok().body(commentService.addComment(comment));
	}

	@PostMapping("/list/{article_id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<CommentDto>> getCommentList(@PathVariable String article_id) {
		return ResponseEntity.ok().body(commentService.getCommentList(Long.parseLong(article_id)));
	}
}
