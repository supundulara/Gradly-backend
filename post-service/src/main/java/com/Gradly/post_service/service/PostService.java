package com.Gradly.post_service.service;

import com.Gradly.post_service.models.Post;
import com.Gradly.post_service.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post){
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(String id){
        return postRepository.findById(id).orElse(null);
    }

    public void deletePost(String id){
        postRepository.deleteById(id);
    }
}