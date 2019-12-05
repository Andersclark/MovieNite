package com.java18.movienight.controllers;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.MoviePreview;
import com.java18.movienight.services.AtlasService;
import com.java18.movienight.services.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    AtlasService atlasService;
    @Autowired
    OmdbService omdbService;

    @GetMapping("/{id}")
    private ResponseEntity getById(@PathVariable String id) {
        //Optional<Movie> internalResult = atlasService.findById(id);  //TODO: Use service to get data from both OMDB and AtlasCloud
       // if(internalResult.isPresent()){
       //     return new ResponseEntity<>(internalResult.get(), HttpStatus.OK);
        //} else {
            Movie omdbResult = omdbService.findById(id);
            return new ResponseEntity(HttpStatus.OK);
        //omdbResult.map(movie -> (new ResponseEntity<>(movie, HttpStatus.OK))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        //}
    }

    @GetMapping("/find/")
    private ResponseEntity findByTitle(@RequestParam String title) {
        List<MoviePreview> result = new ArrayList(atlasService.findByTitleContaining(title)); //TODO: Use service to get data from both OMDB and AtlasCloud (2)
        if(result.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else { return new ResponseEntity<>(result, HttpStatus.OK); }
    }


}
