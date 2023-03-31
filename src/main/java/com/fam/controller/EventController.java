package com.fam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fam.model.Event;
import com.fam.model.User;
import com.fam.service.EventService;
import com.fam.service.UserService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<Event> createEvent(@RequestParam("title") String title,
        @RequestParam("date") String date,
        @RequestParam("startTime") Long startTime,
        @RequestParam("endTime") Long endTime,
        @RequestParam("category") String category,
        @RequestParam(value = "completed", defaultValue = "false") boolean completed,
        @RequestParam(value = "notes", required = false) String notes,
        @RequestParam(value = "user", required = false) Long userId) {
        	Instant startInstant = Instant.ofEpochMilli(startTime);
        	LocalTime startTimeLocal = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault()).toLocalTime();

        	Instant endInstant = Instant.ofEpochMilli(endTime);
        	LocalTime endTimeLocal = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault()).toLocalTime();

        	try {
            // create the event object from the request parameters
            Event event = new Event();
            event.setTitle(title);
            event.setDate(LocalDate.parse(date));
            event.setStartTime(startTimeLocal);
        	event.setEndTime(endTimeLocal);
            event.setCategory(category);
            event.setCompleted(completed);
            event.setNotes(notes);
            if (userId != null) {
                User user = userService.getUserById(userId);
                event.setUser(user);
            }

            Event savedEvent = eventService.saveEvent(event);
            return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
        }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getEventsByUserId(@PathVariable Long userId) {
        List<Event> events = eventService.getEventsByUserId(userId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event existingEvent = eventService.getEventById(id);
        if (existingEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDate(event.getDate());
        existingEvent.setStartTime(event.getStartTime());
        existingEvent.setEndTime(event.getEndTime());
        existingEvent.setCategory(event.getCategory());
        existingEvent.setCompleted(event.isCompleted());
        existingEvent.setNotes(event.getNotes());
        Event savedEvent = eventService.saveEvent(existingEvent);
        return new ResponseEntity<>(savedEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventService.deleteEvent(event);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}