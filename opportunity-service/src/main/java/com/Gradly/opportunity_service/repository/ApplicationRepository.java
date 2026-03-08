package com.Gradly.opportunity_service.repository;

import com.Gradly.opportunity_service.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {

    List<Application> findByUserId(String userId);

    boolean existsByJobIdAndUserId(String jobId, String userId);
}