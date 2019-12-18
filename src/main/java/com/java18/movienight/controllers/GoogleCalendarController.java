package com.java18.movienight.controllers;

import com.java18.movienight.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calendar")
public class GoogleCalendarController {
  @Autowired
  GoogleCalendarService googleCalendarService;

  @GetMapping
  public ResponseEntity<List<Long>> loadEvents(@Param("duration") int duration) {
    return new ResponseEntity<>(googleCalendarService.getEvents(duration), HttpStatus.OK);
  }

}
