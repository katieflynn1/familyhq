package com.calendar.demo.services;

import java.util.List;

import com.calendar.demo.entities.Event;
import com.calendar.demo.entities.EventEntity;

public interface EventService {

	public List<EventEntity> findAll();
	
	public List<Event> findAllEvents();
	
	public void create(Event event);
	
	public Event find(int id);
	
	public void delete(Event event);
	
	public void update(Event event);
	
}
