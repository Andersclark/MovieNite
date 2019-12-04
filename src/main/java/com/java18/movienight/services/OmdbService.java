package com.java18.movienight.services;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.MoviePreview;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OmdbService {

    public OmdbService(){}

    public Optional<Movie> findById(String id){
        final String uri = ("https://www.omdbapi.com/?apikey=a99e9d48&i=" + id);
        RestTemplate restTemplate = new RestTemplate();
        try {
            Movie result = restTemplate.getForObject(uri, Movie.class);
            return Optional.ofNullable(result);

        } catch(RestClientException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
