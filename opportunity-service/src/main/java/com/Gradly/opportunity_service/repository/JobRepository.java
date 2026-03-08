package com.Gradly.opportunity_service.repository;

import com.Gradly.opportunity_service.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}
