package com.java18.movienight.repositories;

import com.java18.movienight.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepo extends MongoRepository<Event, String> {
    List<Event> findByMovieId(String movieId);
    List<Event> findByOrganizerId(String _Id);


}