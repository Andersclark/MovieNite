package com.java18.movienight.controllers;

import com.java18.movienight.entities.RequestLog;
import com.java18.movienight.models.Movie;
import com.java18.movienight.models.SearchResult;
import com.java18.movienight.repositories.RequestLogRepo;
import com.java18.movienight.services.AtlasService;
import com.java18.movienight.services.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    AtlasService atlasService;
    @Autowired
    OmdbService omdbService;
    @Autowired
    RequestLogRepo logRepo;

    @GetMapping("/{imdbId}")
    private ResponseEntity findByImdbId(@PathVariable String imdbId) {
        Optional<Movie> internalResult = atlasService.findById(imdbId);
        RequestLog log = new RequestLog("/api/v1/movies/{imdbId}", "GET");

        if (internalResult.isPresent()) {
            log.statusCode = "200";
            logRepo.save(log);
            return new ResponseEntity<>(internalResult.get(), HttpStatus.OK);
        } else {
            Optional<Movie> omdbResult = omdbService.findByImdbId(imdbId);
            omdbResult.ifPresent(movie -> atlasService.saveMovie(movie));


            if(omdbResult.isPresent()){
                log.statusCode = "200";
                logRepo.save(log);
                return new ResponseEntity<>(omdbResult, HttpStatus.OK);
            }
            else {
                log.statusCode = "404";
                logRepo.save(log);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping("/search")
    private ResponseEntity<SearchResult> searchByTitleContaining(@RequestParam String title, @RequestParam Integer page) {
        ResponseEntity<SearchResult> response;
        RequestLog log = new RequestLog(("/api/v1/movies//search?title="+title+"&&page="+page.toString()), "GET");

        if (title.length() < 3) {
            log.statusCode = "400";
            logRepo.save(log);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search must be at least three characters");
        }
        String titleToSend = title + "&page=" + page.toString();
        SearchResult results = atlasService.findBySearchString(titleToSend);
        if (results == null) {
            results = omdbService.searchByTitleContaining(titleToSend);
            results.setSearchString(titleToSend);
            atlasService.saveSearchResults(results);
        }
        if (results.getMovies() == null) {
            log.statusCode = "404";
            logRepo.save(log);
            response = new ResponseEntity<>(results, HttpStatus.NOT_FOUND);
        } else {
            log.statusCode = "200";
            logRepo.save(log);
            response = new ResponseEntity<>(results, HttpStatus.OK);
        }
        return response;
    }


}
