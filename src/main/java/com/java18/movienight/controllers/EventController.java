package com.java18.movienight.controllers;

import com.java18.movienight.models.Event;
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

        @GetMapping("/{id}")
        private ResponseEntity findById(@PathVariable String id) {
            Optional<Event> result = eventService.findById(id);
            return result.map(movie -> (new ResponseEntity<>(result.get(), HttpStatus.OK))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    @GetMapping("/user/{organizerId}")
    private ResponseEntity findByOrganizerId(@PathVariable String organizerId) {

        List<Event> result = eventService.findByOrganizerId(organizerId);
        if(result == null || result.size()<1){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<List<Event>>(result, HttpStatus.OK);
        }
    }
    @GetMapping("/movieId/{movieId}")
    private ResponseEntity findByMovieId(@PathVariable String movieId){
            List<Event> result = eventService.findByMovieId(movieId);
            if(result == null || result.size()<1){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity(result, HttpStatus.OK);
            }
    }
    @PostMapping
    private ResponseEntity saveEvent(@RequestBody Event event){
            Event result = eventService.insertEvent(event);
            if(result == null){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<Event>(result, HttpStatus.OK);
            }
    }
}
