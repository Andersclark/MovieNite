package com.java18.movienight.controllers;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.MoviePreview;
import com.java18.movienight.models.OmdbSearchResult;
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
    private ResponseEntity findByImdbId(@PathVariable String imdbId) {
        Optional<Movie> internalResult = atlasService.findById(imdbId);
        if(internalResult.isPresent()){
             return new ResponseEntity<>(internalResult.get(), HttpStatus.OK);
        } else {
            Optional<Movie> omdbResult = omdbService.findByImdbId(imdbId);
            return omdbResult.map(movie -> (new ResponseEntity<>(movie, HttpStatus.OK))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping("/search")
    private ResponseEntity searchByTitleContaining(@RequestParam String title) {
        OmdbSearchResult results = omdbService.searchByTitleContaining(title);
        if(results.getMovies().isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else { return new ResponseEntity<>(results.getMovies(), HttpStatus.OK); }
    }


}
