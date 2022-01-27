package org.launchcode.VennTime.controllers;

import org.launchcode.VennTime.data.EventRepository;
import org.launchcode.VennTime.models.Event;
import org.launchcode.VennTime.models.dto.CreateEventDTO;
import org.launchcode.VennTime.models.mapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private DTOMapper dtoMapper;

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("createEventDTO", new CreateEventDTO());
        return "home";
    }

    @ResponseBody
    @PostMapping("/")
    public String processCreateEvent(Model model, @ModelAttribute @Valid CreateEventDTO createEventDTO, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            return "home";
        }

            Event newEvent = dtoMapper.toEvent(createEventDTO);
            eventRepository.save(newEvent);
            return newEvent.getName();
        }
    }
