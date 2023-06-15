package com.assignment.se.service;

import com.assignment.se.repository.ArticleRepository;
import com.assignment.se.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	private final BoardRepository boardRepository;
	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleService(BoardRepository boardRepository, ArticleRepository articleRepository) {
		this.boardRepository = boardRepository;
		this.articleRepository = articleRepository;
	}


}
