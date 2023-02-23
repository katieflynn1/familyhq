package com.calendar.demo.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.calendar.demo.services.EventService;
import com.google.gson.Gson;

@Controller
@RequestMapping("api/event")
public class EventRestController {

	@Autowired
	private EventService eventService;
	
	@RequestMapping(value = "findall", method = RequestMethod.GET)
	@ResponseBody
	public String findall() {
		Gson gson = new Gson();
		return gson.toJson(eventService.findAll());
	}
	
}
