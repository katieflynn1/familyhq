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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class EventController {

    private final EventRepository er;
    private final UserRepository ur ;

    @Autowired
    public EventController(EventRepository er,UserRepository ur) {
        this.er = er;
        this.ur = ur;
    }

    @GetMapping("/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return er.findBetween(start, end);
    }

    @PostMapping("/api/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    public Event createEvent(@RequestParam("title") String title,
                             @RequestParam("start") LocalDateTime start,
                             @RequestParam("end") LocalDateTime end,
                             @RequestParam("category") String category,
                             @RequestParam("completed") boolean completed,
                             @RequestParam("notes") String notes,
                             @RequestParam("color") String color,
                             @RequestParam(value = "userId", required = false) Long userId) {

        Event e = new Event();
        e.setTitle(title);
        e.setStart(start);
        e.setEnd(end);
        e.setCategory(category);
        e.setCompleted(completed);
        e.setNotes(notes);
        e.setColor(color);
        if (userId != null) {
            User user = ur.findById(userId).get();
            e.setUser(user);
        }

        er.save(e);
        return e;
    }
    @PostMapping("/create-event")
    public String createEvent(@ModelAttribute("eventForm") EventCreateParams params) {

        Event e = new Event();
        e.setTitle(params.title);
        e.setStart(params.start);
        e.setEnd(params.end);
        e.setCategory(params.category);
        e.setCompleted(params.completed);
        e.setNotes(params.notes);
        e.setColor(params.color);
        if (params.userId != null) {
            User user = ur.findById(params.userId).get();
            e.setUser(user);
        }

        er.save(e);

        return "redirect:/admin/dashboard";
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

    @PostMapping("/api/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event setColor(@RequestBody SetColorParams params) {

        Event e = er.findById(params.id).get();
        e.setColor(params.color);
        er.save(e);

        return e;
    }

    public static class EventCreateParams {
        public String title;
        public LocalDateTime start;
        public LocalDateTime end;
        public String category;
        public boolean completed;
        public String notes;
        public String color;
        public User user;
        public Long userId;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }


}