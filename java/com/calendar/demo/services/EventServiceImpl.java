package com.calendar.demo.services;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.calendar.demo.dao.EventDAO;
import com.calendar.demo.entities.Event;
import com.calendar.demo.entities.EventEntity;

@Repository("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDAO eventDAO;
	
	@Override
	public List<EventEntity> findAll() {
		return eventDAO.findAll();
	}

	@Override
	public void create(Event event) {
		eventDAO.create(event);
	}

	@Override
	public List<Event> findAllEvents() {
		return eventDAO.findAllEvents();
	}

	@Override
	public Event find(int id) {
		return eventDAO.find(id);
	}

	@Override
	public void delete(Event event) {
		eventDAO.delete(event);
	}

	@Override
	public void update(Event event) {
		eventDAO.update(event);
	}

}
