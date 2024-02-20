package com.blog.controller;

import com.blog.vo.Comment;
import com.blog.vo.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.blog.service.CommentService;


import java.util.List;

@RestController
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public Object savePost(HttpServletResponse response, @RequestBody Comment commentParam) {
        Comment comment = new Comment(commentParam.getPostId(), commentParam.getUser(), commentParam.getComment());
        boolean isSuccess = commentService.saveComment(comment);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam("post_id") Long postId) {
        return commentService.getCommentList(postId);
    }

    @GetMapping("/comment")
    public Comment getPost(@RequestParam("id") Long id) {
        Comment comment = commentService.getComment(id);
        return comment;
    }

    @DeleteMapping("/comment")
    public Object deletePost(HttpServletResponse response, @RequestParam("id") Long id) {
        boolean isSuccess = commentService.deletePost(id);
        log.info("Id:: " + id);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }
    }
    @GetMapping("/comments/search")
    public List<Comment> searchComments(
            @RequestParam("post_id") Long postId,
            @RequestParam("query") String query
    ) {
        return commentService.searchComments(postId, query);
    }
}
