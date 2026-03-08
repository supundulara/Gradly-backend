package com.Gradly.post_service.repository;

import com.Gradly.post_service.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {

    List<Comment> findByPostId(String postId);
    long countByPostId(String postId);


}