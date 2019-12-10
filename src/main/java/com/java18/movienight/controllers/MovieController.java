package com.java18.movienight.controllers;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.SearchResult;
import com.java18.movienight.services.AtlasService;
import com.java18.movienight.services.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    AtlasService atlasService;
    @Autowired
    OmdbService omdbService;

    @GetMapping("/{imdbId}")
    private ResponseEntity findByImdbId(@PathVariable String imdbId) {
        Optional<Movie> internalResult = atlasService.findById(imdbId);
        if (internalResult.isPresent()) {
            return new ResponseEntity<>(internalResult.get(), HttpStatus.OK);
        } else {
            Optional<Movie> omdbResult = omdbService.findByImdbId(imdbId);
            omdbResult.ifPresent(movie -> atlasService.saveMovie(movie));
            return omdbResult.map(movie -> (new ResponseEntity<>(movie, HttpStatus.OK))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping("/search")
    private ResponseEntity<SearchResult> searchByTitleContaining(@RequestParam String title, @RequestParam Integer page) {
        ResponseEntity<SearchResult> response;
        if(title.length()<3){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return response;
        }
        String titleToSend = title + "&page=" + page.toString();
        SearchResult results = atlasService.findBySearchString(titleToSend);
        if (results == null) {
            results = omdbService.searchByTitleContaining(titleToSend);
            results.setSearchString(titleToSend);
            atlasService.saveSearchResults(results);
        }
        if (results.getMovies().size() < 1) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(results, HttpStatus.OK);
        }
        return response;
    }


}
