package com.fam.controller;

import com.fam.model.Event;
import com.fam.model.User;
import com.fam.repository.EventRepository;
import com.fam.repository.UserRepository;
import com.fam.service.UserService;
import com.fam.service.UserServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
public class EventController {
    private final EventRepository er;
    private final UserRepository ur ;
    private final UserService userService;

    @Autowired
    public EventController(EventRepository er,UserRepository ur, UserService userService) {
        this.er = er;
        this.ur = ur;
        this.userService = userService;
    }

    @GetMapping("/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return er.findBetween(start, end);
    }

    @PostMapping("/api/events/create")
    @Transactional
    public Event createEvent(@RequestBody EventCreateParams params){

            User userObj = userService.getUserByEmail(params.email);
            Event e = new Event();
            e.setTitle(params.title);
            e.setStart(params.start);
            e.setEnd(params.end);
            e.setCategory(params.category);
            e.setCompleted(params.completed);
            e.setNotes(params.notes);
            if (params.email != null) {
            e.setUser(userObj);
        }

        er.save(e);
        return e;
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
        User userObj = userService.getUserByEmail(params.email);
        Event e = er.findById(params.id).orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + params.id));
        e.setTitle(params.title);
        e.setCategory(params.category);
        e.setCompleted(params.completed);
        e.setNotes(params.notes);
        if (params.email != null) {
            e.setUser(userObj);}
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