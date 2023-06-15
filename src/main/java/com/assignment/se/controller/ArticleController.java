package com.assignment.se.controller;

import com.assignment.se.dto.ArticleDto;
import com.assignment.se.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/article")
public class ArticleController {
	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping(value="/add", consumes = {"multipart/form-data"})
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<Object>> addArticle(
			@ModelAttribute ArticleDto article) throws Exception {

		ArticleDto addedArticle = articleService.addArticle(article);
		if (article.getFiles() != null)
			return ResponseEntity.ok().body(List.of(addedArticle, articleService.addArticleFiles(addedArticle.getId(), article.getFiles())));
		return ResponseEntity.ok().body(List.of(addedArticle));
	}

	@PostMapping("/get")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<ArticleDto> getArticle(@RequestBody Map<String, Long> params) {
		return ResponseEntity.ok(articleService.getArticle(params.get("article_id")));
	}

	@GetMapping("/list/{board_id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<ArticleDto>> getArticleList(@PathVariable String board_id) {
		return ResponseEntity.ok(articleService.getArticleList(Long.parseLong(board_id)));
	}

	@PutMapping("/update")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<ArticleDto> updateArticle(@RequestBody ArticleDto article) {
		return ResponseEntity.ok(articleService.updateArticle(article));
	}

	@DeleteMapping("/delete/{article_id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<ArticleDto> deleteArticle(@PathVariable String article_id) {
		return ResponseEntity.ok(articleService.deleteArticle(Long.parseLong(article_id)));
	}
}
