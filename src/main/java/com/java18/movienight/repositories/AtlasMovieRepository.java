package com.java18.movienight.repositories;

import com.java18.movienight.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AtlasMovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitleContaining(String title);
    Optional<Movie> findByImdbId(String imdbId);
}