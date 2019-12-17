package com.java18.movienight.controllers;

import com.java18.movienight.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/calendar")
public class GoogleCalendarController {
  @Autowired
  GoogleCalendarService googleCalendarService;

  @GetMapping
  public String loadEvents() {
    return googleCalendarService.getEvents();
  }

}
