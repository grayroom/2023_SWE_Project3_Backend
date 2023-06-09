package com.assignment.se.service;

import com.assignment.se.dto.ArticleDto;
import com.assignment.se.dto.ArticleGetDto;
import com.assignment.se.entity.Article;
import com.assignment.se.repository.ArticleRepository;
import com.assignment.se.repository.BoardRepository;
import com.assignment.se.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {

	@Value("${article.file.path}")
	private String ARTICLE_FILE_PATH;
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

	public ArticleGetDto getArticle(Long id) {
		List<File> files = new ArrayList<>();
		List<String> filePathList = new ArrayList<>();
		// from ARTICLE_FILE_PATH + id read all files
		Path path = Path.of(ARTICLE_FILE_PATH, id.toString());
		try {
			Files.walk(path).forEach(file -> {
				files.add(file.toFile());
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		// for all file, cut the path at the beginning of "resource"
		files.forEach(file -> {
			// if file is directory, skip
			if (file.isDirectory()) {
				return;
			}
			String filePath = file.getPath();
			filePath = filePath.substring(filePath.indexOf("resource") + 9);
			filePathList.add(filePath);
		});

		Article respArticle = articleRepository.findById(id).orElse(null);
		return ArticleGetDto.from(Objects.requireNonNull(respArticle), filePathList);
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

	public ArticleGetDto deleteArticle(long l) {
		ArticleGetDto article = getArticle(l);
		articleRepository.deleteById(l);
		return article;
	}

	public List<String> addArticleFiles(Long article_id, List<MultipartFile> files) throws Exception {
		List<String> resList = new ArrayList<>();
		Article article = articleRepository.findById(article_id).orElse(null);
		files.stream().forEach(file -> {
			Path path = Path.of(ARTICLE_FILE_PATH, article.getId().toString(), file.getOriginalFilename());
			try {
				Files.createDirectories(path.getParent());
				Files.copy(file.getInputStream(), path);
				resList.add( Path.of("/resource/article", article.getId().toString(), file.getOriginalFilename()).toString());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		return resList;
	}
}
