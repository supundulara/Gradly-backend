package com.Gradly.post_service.repository;

import com.Gradly.post_service.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like, String> {
    boolean existsByPostIdAndUserId(String postId, String userId);

    long countByPostId(String postId);

}
