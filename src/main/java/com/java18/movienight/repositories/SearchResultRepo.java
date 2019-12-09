package com.java18.movienight.repositories;

import com.java18.movienight.models.SearchResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SearchResultRepo extends MongoRepository<SearchResult, String> {
        SearchResult findBySearchString(String searchString);
}

