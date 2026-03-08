package com.Gradly.opportunity_service.repository;

import com.Gradly.opportunity_service.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}