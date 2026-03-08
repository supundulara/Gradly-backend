package com.Gradly.opportunity_service.repository;

import com.Gradly.opportunity_service.model.EventRSVP;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRSVPRepository extends MongoRepository<EventRSVP, String> {

    Optional<EventRSVP> findByEventIdAndUserId(String eventId, String userId);

    List<EventRSVP> findByEventId(String eventId);

    void deleteByEventIdAndUserId(String eventId, String userId);
}