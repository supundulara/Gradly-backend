package com.Gradly.post_service.service;

import com.Gradly.post_service.dto.CreatePostRequest;
import com.Gradly.post_service.dto.EventNotificationMessage;
import com.Gradly.post_service.dto.PostResponse;
import com.Gradly.post_service.dto.UserResponse;
import com.Gradly.post_service.models.Comment;
import com.Gradly.post_service.models.Like;
import com.Gradly.post_service.models.Post;
import com.Gradly.post_service.repository.CommentRepository;
import com.Gradly.post_service.repository.LikeRepository;
import com.Gradly.post_service.repository.PostRepository;
import com.Gradly.post_service.client.UserClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UserClient userClient;
    private final EventPublisher eventPublisher;

    public PostService(PostRepository postRepository, LikeRepository likeRepository, CommentRepository commentRepository, UserClient userClient, EventPublisher eventPublisher) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.userClient = userClient;
        this.eventPublisher = eventPublisher;
    }

    public Post createPost(CreatePostRequest request, String userId) {

        UserResponse user = userClient.getUser(userId);

        Post post = new Post();
        post.setAuthorId(userId);
        post.setAuthorName(user.getName());
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
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

        // Get post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Get users
        UserResponse actor = userClient.getUser(userId);
        UserResponse creator = userClient.getUser(post.getAuthorId());

        // Publish notification event
        eventPublisher.publishEvent(
                EventNotificationMessage.builder()
                        .type("POST_LIKE")
                        .postId(postId)
                        .creatorId(post.getAuthorId())
                        .creatorName(creator.getName())
                        .actorId(userId)
                        .actorName(actor.getName())
                        .build()
        );
    }
    public void unlikePost(String postId, String userId){

        likeRepository.findByPostIdAndUserId(postId,userId)
                .ifPresent(likeRepository::delete);
    }
    public long getLikeCount(String postId){
        return likeRepository.countByPostId(postId);
    }
    public Comment addComment(String postId, String userId, String content){

        UserResponse user = userClient.getUser(userId);

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .userName(user.getName())
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        Comment savedComment = commentRepository.save(comment);

        // Get post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Post owner
        UserResponse creator = userClient.getUser(post.getAuthorId());

        // Publish notification
        eventPublisher.publishEvent(
                EventNotificationMessage.builder()
                        .type("POST_COMMENT")
                        .postId(postId)
                        .creatorId(post.getAuthorId())
                        .creatorName(creator.getName())
                        .actorId(userId)
                        .actorName(user.getName())
                        .build()
        );

        return savedComment;
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
                        .authorName(post.getAuthorName())
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
                .authorName(post.getAuthorName())
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