package com.calendar.demo.dao;

import java.util.List;

import com.calendar.demo.entities.Event;
import com.calendar.demo.entities.EventEntity;



public interface EventDAO {

	public List<EventEntity> findAll();
	
	public List<Event> findAllEvents();
	
	public Event find(int id);
	
	public void create(Event event);
	
	public void delete(Event event);
	
	public void update(Event event);
	
}
