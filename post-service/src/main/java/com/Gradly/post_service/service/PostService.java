package com.Gradly.post_service.service;

import com.Gradly.post_service.dto.PostResponse;
import com.Gradly.post_service.models.Comment;
import com.Gradly.post_service.models.Like;
import com.Gradly.post_service.models.Post;
import com.Gradly.post_service.repository.CommentRepository;
import com.Gradly.post_service.repository.LikeRepository;
import com.Gradly.post_service.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, LikeRepository likeRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    public Post createPost(Post post){
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    public void likePost(String postId, String userId){

        if(likeRepository.findByPostIdAndUserId(postId, userId).isPresent()){
            return;
        }

        Like like = Like.builder()
                .postId(postId)
                .userId(userId)
                .build();

        likeRepository.save(like);
    }
    public void unlikePost(String postId, String userId){

        likeRepository.findByPostIdAndUserId(postId,userId)
                .ifPresent(likeRepository::delete);
    }
    public long getLikeCount(String postId){
        return likeRepository.countByPostId(postId);
    }
    public Comment addComment(String postId, String userId, String content){

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }
    public List<Comment> getComments(String postId){
        return commentRepository.findByPostId(postId);
    }

    public List<PostResponse> getAllPosts(){

        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .authorId(post.getAuthorId())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .likeCount(likeRepository.countByPostId(post.getId()))
                        .commentCount(commentRepository.countByPostId(post.getId()))
                        .build())
                .toList();
    }

    public PostResponse getPostById(String id){

        Post post = postRepository.findById(id)
                .orElseThrow();

        return PostResponse.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .likeCount(likeRepository.countByPostId(id))
                .commentCount(commentRepository.countByPostId(id))
                .build();
    }

    public void deletePost(String postId, String userId){

        Post post = postRepository.findById(postId)
                .orElseThrow();

        if(!post.getAuthorId().equals(userId)){
            throw new RuntimeException("Unauthorized");
        }

        postRepository.delete(post);
    }
}