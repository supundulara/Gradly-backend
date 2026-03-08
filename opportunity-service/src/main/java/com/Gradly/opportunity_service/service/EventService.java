package com.Gradly.opportunity_service.service;

import com.Gradly.opportunity_service.client.UserClient;
import com.Gradly.opportunity_service.dto.CreateEventRequest;
import com.Gradly.opportunity_service.dto.EventNotificationMessage;
import com.Gradly.opportunity_service.dto.EventResponse;
import com.Gradly.opportunity_service.dto.UserResponse;
import com.Gradly.opportunity_service.model.Event;
import com.Gradly.opportunity_service.model.EventRSVP;
import com.Gradly.opportunity_service.repository.EventRSVPRepository;
import com.Gradly.opportunity_service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserClient userClient;
    private final EventRSVPRepository eventRSVPRepository;
    private final EventPublisher eventPublisher;

    public EventResponse createEvent(CreateEventRequest request, String userId) {

        Event event = new Event();

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setEventDate(request.getEventDate());
        event.setCreatedBy(userId);
        event.setCreatedAt(LocalDateTime.now());

        Event saved = eventRepository.save(event);

        eventPublisher.publishEvent(
                EventNotificationMessage.builder()
                        .eventId(saved.getId())
                        .title(saved.getTitle())
                        .createdBy(saved.getCreatedBy())
                        .build()
        );

        return mapToResponse(saved);
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EventResponse getEventById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return mapToResponse(event);
    }
    public void rsvpEvent(String eventId, String userId) {

        if (eventRSVPRepository.findByEventIdAndUserId(eventId, userId).isPresent()) {
            throw new RuntimeException("Already RSVPed");
        }

        EventRSVP rsvp = new EventRSVP();
        rsvp.setEventId(eventId);
        rsvp.setUserId(userId);
        rsvp.setCreatedAt(LocalDateTime.now());

        eventRSVPRepository.save(rsvp);
    }
    public void cancelRSVP(String eventId, String userId) {

        eventRSVPRepository.deleteByEventIdAndUserId(eventId, userId);
    }
    public List<UserResponse> getParticipants(String eventId) {

        List<EventRSVP> rsvps = eventRSVPRepository.findByEventId(eventId);

        return rsvps.stream()
                .map(rsvp -> userClient.getUser(rsvp.getUserId()))
                .toList();
    }

    private EventResponse mapToResponse(Event event) {

        UserResponse user = userClient.getUser(event.getCreatedBy());

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .eventDate(event.getEventDate())
                .createdBy(event.getCreatedBy())
                .creatorName(user.getName())
                .createdAt(event.getCreatedAt())
                .build();
    }
}