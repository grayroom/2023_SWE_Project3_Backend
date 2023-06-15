package com.assignment.se.repository;

import com.assignment.se.entity.Article;
import com.assignment.se.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findAllByBoard(Board board);
}
