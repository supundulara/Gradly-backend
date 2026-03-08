package com.Gradly.post_service.repository;

import com.Gradly.post_service.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LikeRepository extends MongoRepository<Like, String> {

    Optional<Like> findByPostIdAndUserId(String postId, String userId);

    long countByPostId(String postId);
}
