package com.java18.movienight.services;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.SearchResult;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@NoArgsConstructor
@Service
public class OmdbService {
    private  static final String OMDB_URL = "https://www.omdbapi.com/?apikey=a99e9d48";

    public Optional findByImdbId(String imdbId) {
        String uri = (OMDB_URL + "&i=" + imdbId);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return Optional.ofNullable(restTemplate.getForObject(uri, Movie.class));
        } catch(RestClientException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public SearchResult searchByTitleContaining(String title){
        String uri = (OMDB_URL +  "&s=" + title);
        SearchResult results;
        try {
            RestTemplate temp = new RestTemplate();
            results = temp.getForObject(uri, SearchResult.class);
            return results;
        } catch (Exception e){
            e.printStackTrace();
            results = new SearchResult();
        } return results;
    }
}
