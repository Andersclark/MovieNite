package com.java18.movienight.controllers;

import com.java18.movienight.entities.RequestLog;
import com.java18.movienight.models.Event;
import com.java18.movienight.repositories.RequestLogRepo;
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
        @Autowired
        RequestLogRepo logRepo;

        @GetMapping("{id}")
        private ResponseEntity findById(@PathVariable String id) {
            RequestLog log = new RequestLog("/api/v1/events"+id, "GET");
            log.statusCode = "200";
            logRepo.save(log);

            Optional<Event> result = eventService.findById(id);
            return result.map(movie -> (new ResponseEntity<>(result.get(), HttpStatus.OK))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    @GetMapping("{organizerId}")
    private ResponseEntity findByOrganizerId(@PathVariable String organizerId) {
        RequestLog log = new RequestLog("/api/v1/events"+organizerId, "GET");

        List<Event> result = eventService.findByOrganizerId(organizerId);
        if(result == null || result.size()<1){
            log.statusCode = "401";
            logRepo.save(log);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            log.statusCode = "200";
            logRepo.save(log);
            return new ResponseEntity<List<Event>>(result, HttpStatus.OK);
        }
    }
    @GetMapping("{movieId}")
    private ResponseEntity findByMovieId(@PathVariable String movieId){
            RequestLog log = new RequestLog("/api/v1/events"+movieId, "GET");
            List<Event> result = eventService.findByMovieId(movieId);
            if( result == null || result.size() < 1 ){
                log.statusCode = "401";
                logRepo.save(log);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else {
                log.statusCode = "200";
                logRepo.save(log);
                return new ResponseEntity(result, HttpStatus.OK);
            }
    }
    @PostMapping
    private ResponseEntity saveEvent(@RequestBody Event event){
            RequestLog log = new RequestLog("/api/v1/events", "POST");
            log.requestBody = event.toString();
            Event result = eventService.insertEvent(event);

            if(result == null){
                log.statusCode = "400";
                logRepo.save(log);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            else {
                log.statusCode = "200";
                logRepo.save(log);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
    }
}
