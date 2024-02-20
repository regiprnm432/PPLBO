package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.blog.service.PostService;
import com.blog.vo.Post;
import com.blog.vo.Result;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;


    @GetMapping("/post")
    public Post getPost(@RequestParam ("id") Long id) {
        Post post = postService.getPost(id);
        return post;
    }
    
    @GetMapping("/posts")
    public List<Post> getPosts() {
        List<Post> posts = postService.getPosts();
        
        return posts;
    }

    @GetMapping("/post/{id}")
    public Post getPostPathParam(@PathVariable ("id") Long id) {
        Post post = postService.getPost(id);
        return post;
    }

    @GetMapping("/posts/updtdate/asc")
    public List<Post> getPostsOrderByUpdtAsc(){
        List<Post> posts = postService.getPostsOrderByUpdtAsc();
        return posts;
    }

    @GetMapping("/posts/regdate/desc")
    public List<Post> getPostsOrderByRegDesc(){
        List<Post> posts = postService.getPostsOrderByRegDesc();
        return posts;
    }

    @GetMapping("/posts/search/title")
    public List<Post> searchByTitle(@RequestParam ("query") String query) {
        List<Post> posts = postService.searchPostByTitle(query);
        return posts;
    }

    @GetMapping("/posts/search/content")
    public List<Post> searchByContent(@RequestParam ("query") String query){
        List<Post> posts = postService.searchPostByContent(query);
        return posts;
    }

    @PostMapping("/post")
    public Object savePost(HttpServletResponse response, @RequestBody Post postParam) {
        Post post = new Post(postParam.getUser(), postParam.getTitle(), postParam.getContent());
        boolean isSuccess = postService.savePost(post);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }

    }

    @DeleteMapping("/post")
    public Object deletePost(HttpServletResponse response, @RequestParam("id") Long id) {
        boolean isSuccess = postService.deletePost(id);
        log.info("Id:: " + id);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }
    }

    @PutMapping("/post")
    public Object modifyPost(HttpServletResponse response, @RequestBody Post postParam) {
        Post post = new Post(postParam.getId(), postParam.getTitle(), postParam.getContent());
        boolean isSuccess = postService.updatePost(post);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }
    }
    
}