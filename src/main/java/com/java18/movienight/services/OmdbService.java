package com.java18.movienight.services;

import com.java18.movienight.models.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class OmdbService {
    private  static final String OMDB_URL = "https://www.omdbapi.com/?apikey=a99e9d48";
    public OmdbService(){}

    public Movie findById(String id) {

        String uri = (OMDB_URL + "&i=" + id);
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();

        try {

            Movie response = restTemplate.getForObject("https://www.omdbapi.com/?apikey=a99e9d48&i=tt0096895", Movie.class);
            System.out.println(response.toString());
            // Movie movie = (Movie)response;
            //System.out.println(movie.title);
            return response;

        } catch(RestClientException e){
            e.printStackTrace();
            return new Movie();
        }
    }
}
