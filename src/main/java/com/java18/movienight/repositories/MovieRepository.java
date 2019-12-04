package com.java18.movienight.repositories;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.MoviePreview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitleContaining(String title);
    Optional<Movie> findById(String id);
}
