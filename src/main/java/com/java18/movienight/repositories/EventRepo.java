package com.java18.movienight.repositories;

import com.java18.movienight.models.ReqEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepo extends MongoRepository<ReqEvent, String> {
    List<ReqEvent> findByMovieId(String movieId);
    List<ReqEvent> findByOrganizerId(String _Id);


}