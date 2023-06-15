package com.assignment.se.service;

import com.assignment.se.dto.CommentDto;
import com.assignment.se.entity.Article;
import com.assignment.se.entity.Comment;
import com.assignment.se.repository.ArticleRepository;
import com.assignment.se.repository.CommentRepository;
import com.assignment.se.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;
	private final UserAuthRepository userAuthRepository;

	@Autowired
	public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, UserAuthRepository userAuthRepository) {
		this.commentRepository = commentRepository;
		this.articleRepository = articleRepository;
		this.userAuthRepository = userAuthRepository;
	}


	public CommentDto addComment(CommentDto comment) {
		Comment targetComment = new Comment(comment);
		return CommentDto.from(commentRepository.save(targetComment));
	}

	public List<CommentDto> getCommentList(long article_id) {
		Article targetArticle = articleRepository.findById(article_id).orElse(null);
		List<Comment> commentList = commentRepository.findAllByArticle(targetArticle);
		return CommentDto.from(commentList);
	}
}
