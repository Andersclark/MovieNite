package com.java18.movienight.controllers;

import com.java18.movienight.models.ReqEvent;
import com.java18.movienight.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

  @Autowired
  EventService eventService;

  @GetMapping("{id}")
  private ResponseEntity findById(@PathVariable String id) {
    Optional<ReqEvent> result = eventService.findById(id);
    return result.map(movie -> (new ResponseEntity<>(result.get(), HttpStatus.OK))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("{organizerId}")
  private ResponseEntity findByOrganizerId(@PathVariable String organizerId) {

    List<ReqEvent> result = eventService.findByOrganizerId(organizerId);
    if (result == null || result.size() < 1) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<List<ReqEvent>>(result, HttpStatus.OK);
    }
  }

  @GetMapping("{movieId}")
  private ResponseEntity findByMovieId(@PathVariable String movieId) {
    List<ReqEvent> result = eventService.findByMovieId(movieId);
    if (result == null || result.size() < 1) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity(result, HttpStatus.OK);
    }
  }

  @PostMapping
  private ResponseEntity saveEvent(@RequestBody ReqEvent reqEvent) {
    eventService.insertEvent(reqEvent);
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }
}
