package com.java18.movienight.repositories;

import com.java18.movienight.entities.RequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestLogRepo extends MongoRepository<RequestLog, String> {
        RequestLog findBy_id(String id);
}
