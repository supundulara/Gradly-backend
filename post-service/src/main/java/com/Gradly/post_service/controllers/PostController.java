package com.Gradly.post_service.controllers;

import com.Gradly.post_service.dto.CommentRequest;
import com.Gradly.post_service.dto.PostResponse;
import com.Gradly.post_service.models.Comment;
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

    // CREATE POST
    @PostMapping
    public Post createPost(
            @RequestBody Post post,
            @RequestHeader("X-User-Id") String userId){

        post.setAuthorId(userId);

        return postService.createPost(post);
    }

    @GetMapping
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }

    // GET SINGLE POST
    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable String id){
        return postService.getPostById(id);
    }

    // DELETE POST
    @DeleteMapping("/{id}")
    public void deletePost(
            @PathVariable String id,
            @RequestHeader("X-User-Id") String userId){

        postService.deletePost(id, userId);
    }

    @PostMapping("/{postId}/likes")
    public void likePost(
            @PathVariable String postId,
            @RequestHeader("X-User-Id") String userId){

        postService.likePost(postId,userId);
    }
    @DeleteMapping("/{postId}/likes")
    public void unlikePost(
            @PathVariable String postId,
            @RequestHeader("X-User-Id") String userId){

        postService.unlikePost(postId,userId);
    }
    @GetMapping("/{postId}/likes/count")
    public long getLikeCount(@PathVariable String postId){
        return postService.getLikeCount(postId);
    }
    @PostMapping("/{postId}/comments")
    public Comment addComment(
            @PathVariable String postId,
            @RequestBody CommentRequest request,
            @RequestHeader("X-User-Id") String userId){

        return postService.addComment(postId,userId,request.getContent());
    }

    @GetMapping("/{postId}/comments")
    public List<Comment> getComments(@PathVariable String postId){
        return postService.getComments(postId);
    }
}