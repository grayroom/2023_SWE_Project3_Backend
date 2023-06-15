package com.assignment.se.repository;

import com.assignment.se.entity.Article;
import com.assignment.se.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByArticle(Article article);
}
