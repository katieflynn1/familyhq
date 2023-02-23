package com.calendar.demo.controllers;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import com.calendar.demo.entities.Event;
import com.calendar.demo.services.EventService;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("event")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "event/index";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap modelMap) {
		modelMap.put("events", eventService.findAllEvents());
		return "event/list";
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") int id) {
		eventService.delete(eventService.find(id));
		return "redirect:../list.html";
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(
		@PathVariable("id") int id, 
		ModelMap modelMap) {
		modelMap.put("event", eventService.find(id));
		return "event/edit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(
		@ModelAttribute("event") Event event, 
		HttpServletRequest request,
		ModelMap modelMap) {
		try {
			SimpleDateFormat simpleDateFormat = 
					new SimpleDateFormat("MM/dd/yyyy");
			Event currentEvent = eventService.find(event.getId());
			currentEvent.setDescription(event.getDescription());
			currentEvent.setStartDate(simpleDateFormat
					.parse(request.getParameter("startDate")));
			currentEvent.setEndDate(simpleDateFormat
					.parse(request.getParameter("endDate")));
			currentEvent.setName(event.getName());
			eventService.update(currentEvent);
			return "redirect:../event.html";
		} catch (Exception e) {
			modelMap.put("event", event);
			return "event/index";
		}
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(ModelMap modelMap) {
		modelMap.put("event", new Event());
		return "event/add";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(
		@ModelAttribute("event") Event event, 
		HttpServletRequest request,
		ModelMap modelMap) {
		try {
			SimpleDateFormat simpleDateFormat = 
					new SimpleDateFormat("MM/dd/yyyy");
			event.setStartDate(simpleDateFormat
				.parse(request.getParameter("startDate")));
			event.setEndDate(simpleDateFormat
				.parse(request.getParameter("endDate")));
			eventService.create(event);
			return "redirect:../event.html";
		} catch (Exception e) {
			modelMap.put("event", event);
			return "event/index";
		}
	}
	
}
