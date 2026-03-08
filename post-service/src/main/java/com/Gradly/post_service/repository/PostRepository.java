package com.Gradly.post_service.repository;

import com.Gradly.post_service.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
