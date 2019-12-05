package com.java18.movienight.services;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.MoviePreview;
import com.java18.movienight.models.OmdbSearchResult;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.naming.directory.SearchResult;
import java.util.*;

@NoArgsConstructor
@Service
public class OmdbService {
    private  static final String OMDB_URL = "https://www.omdbapi.com/?apikey=a99e9d48";

    public Optional findByImdbId(String id) {
        String uri = (OMDB_URL + "&i=" + id);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return Optional.ofNullable(restTemplate.getForObject(uri, Movie.class));
        } catch(RestClientException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public OmdbSearchResult searchByTitleContaining(String title){
        String uri = (OMDB_URL +  "&s=" + title);
        System.out.println("URI: " + uri);
        OmdbSearchResult results;
        try {
            RestTemplate temp = new RestTemplate();
            results = temp.getForObject(uri, OmdbSearchResult.class);
            System.out.println(results.getMovies().toString());
            return results;
        } catch (Exception e){
            e.printStackTrace();
            results = new OmdbSearchResult();
        } return results;
    }
}
