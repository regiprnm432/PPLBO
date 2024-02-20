package com.blog.service;

import com.blog.vo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blog.repository.CommentJpaRepository;
import com.blog.vo.Comment;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentJpaRepository commentJpaRepository;

    public boolean saveComment(Comment comment) {
        Comment result = (Comment) commentJpaRepository.save(comment);
        boolean isSuccess = true;

        if (result == null) {
            isSuccess = false;
        }

        return isSuccess;
    }

    public List<Comment> getCommentList(Long postId) {
        return commentJpaRepository.findByPostIdOrderByRegDateDesc(postId);
    }

    public Comment getComment(Long id) {
        return commentJpaRepository.findOneById(id);
    }

    public boolean deletePost(Long id) {
        Comment result = commentJpaRepository.findOneById(id);

        if(result == null){
            return false;
        }
        commentJpaRepository.deleteById(id);
        return true;
    }

    public List<Comment> searchComments(Long postId, String query) {
        return commentJpaRepository.findByPostIdAndCommentContainingOrderByRegDateDesc(postId, query);
    }
}
