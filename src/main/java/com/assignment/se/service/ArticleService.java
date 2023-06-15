package com.assignment.se.service;

import com.assignment.se.dto.ArticleDto;
import com.assignment.se.entity.Article;
import com.assignment.se.repository.ArticleRepository;
import com.assignment.se.repository.BoardRepository;
import com.assignment.se.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {
	private final BoardRepository boardRepository;
	private final UserAuthRepository userAuthRepository;
	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleService(BoardRepository boardRepository, UserAuthRepository userAuthRepository,
	                      ArticleRepository articleRepository) {
		this.boardRepository = boardRepository;
		this.userAuthRepository = userAuthRepository;
		this.articleRepository = articleRepository;
	}

	public ArticleDto addArticle(ArticleDto article) {
		Article targetArticle = new Article(article);
		targetArticle.setBoard(boardRepository.findById(article.getBoard_id()).orElse(null));
		targetArticle.setUser(userAuthRepository.findById(article.getUser_id()).orElse(null));
		return ArticleDto.from(articleRepository.save(targetArticle));
	}

	public ArticleDto getArticle(Long id) {
		return ArticleDto.from(Objects.requireNonNull(articleRepository.findById(id).orElse(null)));
	}

	public List<ArticleDto> getArticleList(long l) {
		List<Article> articleList = articleRepository.findAllByBoard(boardRepository.findById(l).orElse(null));
		return ArticleDto.from(articleList);
	}

	public ArticleDto updateArticle(ArticleDto article) {
		articleRepository.findById(article.getId()).ifPresent(targetArticle -> {
			targetArticle.setName(article.getName());
			targetArticle.setContent(article.getContent());
			targetArticle.setType(article.getType());
			articleRepository.save(targetArticle);
		});
		return article;
	}

	public ArticleDto deleteArticle(long l) {
		ArticleDto article = getArticle(l);
		articleRepository.deleteById(l);
		return article;
	}
}
