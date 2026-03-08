package com.Gradly.opportunity_service.controller;

import com.Gradly.opportunity_service.dto.CreateEventRequest;
import com.Gradly.opportunity_service.dto.EventResponse;
import com.Gradly.opportunity_service.dto.UserResponse;
import com.Gradly.opportunity_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public EventResponse createEvent(
            @RequestBody CreateEventRequest request,
            @RequestHeader("X-User-Id") String userId
    ) {
        return eventService.createEvent(request, userId);
    }

    @GetMapping
    public List<EventResponse> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventResponse getEvent(@PathVariable String id) {
        return eventService.getEventById(id);
    }
    @PostMapping("/{eventId}/rsvp")
    public String rsvpEvent(
            @PathVariable String eventId,
            @RequestHeader("X-User-Id") String userId
    ) {
        eventService.rsvpEvent(eventId, userId);
        return "RSVP successful";
    }
    @DeleteMapping("/{eventId}/rsvp")
    public String cancelRSVP(
            @PathVariable String eventId,
            @RequestHeader("X-User-Id") String userId
    ) {
        eventService.cancelRSVP(eventId, userId);
        return "RSVP removed";
    }
    @GetMapping("/{eventId}/participants")
    public List<UserResponse> getParticipants(@PathVariable String eventId) {

        return eventService.getParticipants(eventId);
    }
}