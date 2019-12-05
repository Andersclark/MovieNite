package com.java18.movienight.services;

import com.java18.movienight.models.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class OmdbService {
    public  static final String OMDB_URL = "https://www.omdbapi.com/?apikey=a99e9d48";
    public OmdbService(){}

    public Optional<Movie> findById(String id){
        String uri = (OMDB_URL + "&i=" + id);
        System.out.println(uri);
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
