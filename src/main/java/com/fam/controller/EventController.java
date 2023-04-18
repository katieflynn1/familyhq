package com.fam.controller;

import com.fam.model.Event;
import com.fam.model.User;
import com.fam.params.EventCreateParams;
import com.fam.params.EventUpdateParams;
import com.fam.repository.EventRepository;
import com.fam.repository.UserRepository;
import com.fam.service.UserService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class EventController {
    private final EventRepository er;
    private final UserRepository ur ;
    private final UserService userService;

    @Autowired
    public EventController(EventRepository er,UserRepository ur, UserService userService ) {
        this.er = er;
        this.ur = ur;
        this.userService = userService;
    }

    @GetMapping("/api/events")
    public List<Event> getEvents(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<Event> events = er.findByCreatorEmailOrAssignedUserEmail(email);
        return events;
    }
    @PostMapping("/api/events/create")
    @Transactional
    public Event createEvent(@RequestBody EventCreateParams params){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = ur.findByEmail(authentication.getName()).orElse(null);
            if (user != null) {
                Event e = new Event();
                e.setTitle(params.title);
                e.setStart(params.start);
                e.setEnd(params.end);
                e.setCategory(params.category);
                e.setCompleted(params.completed);
                e.setNotes(params.notes);
                if (params.assignedUserEmail != null) {
                    e.setAssignedUserEmail(params.assignedUserEmail);
                }
                e.setCreator(user);
                er.save(e);
                return e;
            }
        }
        throw new IllegalArgumentException("creator must not be null");
    }


    @PostMapping("/api/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event moveEvent(@RequestBody EventMoveParams params) {

        Event e = er.findById(params.id).get();
        e.setStart(params.start);
        e.setEnd(params.end);

        er.save(e);
        return e;
    }

    @PostMapping("/api/events/setCategory")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event setCategory(@RequestBody SetCategoryParams params) {

        Long id = params.id;
        if (id == null) {
            throw new IllegalArgumentException("Event ID must not be null");
        }
        Event e = er.findById(params.id).get();
        e.setCategory(params.category);
        er.save(e);

        return e;
    }

    @PostMapping("/api/events/update")
    @Transactional
    public Event updateEvent(@RequestBody EventUpdateParams params) {
        Event e = er.findById(params.id).orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + params.id));
        e.setTitle(params.title);
        e.setCategory(params.category);
        e.setCompleted(params.completed);
        e.setNotes(params.notes);
        if (params.assignedUserEmail != null) {
            e.setAssignedUserEmail(params.assignedUserEmail);
        }
        er.save(e);
        return e;
    }

    @GetMapping("/api/events/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("getEventById called with eventId: " + eventId);
        }
        return er.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + eventId));
    }

    @DeleteMapping("/api/events/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {

        er.deleteById(eventId);
    }
    public static class SetCategoryParams {
        public Long id;
        public String category;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
    }

}