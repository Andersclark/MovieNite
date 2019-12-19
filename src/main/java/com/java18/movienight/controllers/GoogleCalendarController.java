package com.java18.movienight.controllers;

import com.java18.movienight.entities.RequestLog;
import com.java18.movienight.repositories.RequestLogRepo;
import com.java18.movienight.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/calendar")
public class GoogleCalendarController {
  @Autowired
  GoogleCalendarService googleCalendarService;
  @Autowired
  RequestLogRepo logRepo;

  @GetMapping
  public ResponseEntity<List<Long>> loadEvents(@Param("duration") int duration) {
    RequestLog log = new RequestLog("/api/calendar/", "GET");
    log.statusCode = "200";
    log.requestBody = duration.toString();
    logRepo.save(log);
    return new ResponseEntity<>(googleCalendarService.getEvents(duration), HttpStatus.OK);
  }

}
