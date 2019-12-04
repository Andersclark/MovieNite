package com.java18.movienight.controllers;

import com.java18.movienight.models.Movie;
import com.java18.movienight.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    MovieRepository collection;

    @GetMapping("/{id}")
    private ResponseEntity getById(@PathVariable String id) {
        Optional<Movie> result = collection.findById(id);
        return result.map(movie -> new ResponseEntity<>(movie, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    private ResponseEntity findByTitle(@RequestParam String title) {
        List<Movie> result = new ArrayList(collection.findByTitleContaining(title));
        if(result.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else{return new ResponseEntity(result, HttpStatus.OK);}
    }


}
