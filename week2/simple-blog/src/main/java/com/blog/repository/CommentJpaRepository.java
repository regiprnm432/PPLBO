package com.blog.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.vo.Comment;

@Repository("commentJpaRepository")
public interface CommentJpaRepository extends JpaRepository<Comment, Serializable>{
    List<Comment> findByPostIdOrderByRegDateDesc(Long postId);
    Comment findOneById(Long id);
    List<Comment> findByPostIdAndCommentContainingOrderByRegDateDesc(Long postId, String query);

}
