package com.Gradly.post_service.controllers;

import com.Gradly.post_service.models.Post;
import com.Gradly.post_service.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id){
        return postService.getPostById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id){
        postService.deletePost(id);
    }
    @PostMapping("/{postId}/likes")
    public void likePost(@PathVariable String postId, Authentication authentication){

        String userId = authentication.getName();

        postService.likePost(postId, userId);
    }
}