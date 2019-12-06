package com.java18.movienight.services;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.SearchResult;
import com.java18.movienight.repositories.AtlasMovieRepository;
import com.java18.movienight.repositories.SearchResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtlasService {

    @Autowired
    AtlasMovieRepository movieCollection;
    @Autowired
    SearchResultRepo searchCollection;

    public AtlasService() {  }

    public Optional<Movie> findById(String id){
        return movieCollection.findById(id);
    }
    public List<Movie> findByTitleContaining(String title){
        return movieCollection.findByTitleContaining(title);
    }

    public SearchResult findBySearchString(String searchString){
        return searchCollection.findBySearchString(searchString);
    }
    public Optional findSearchById(String id){
        return searchCollection.findById(id);
    }
    public void saveSearchResults(SearchResult searchResults){
        searchCollection.save(searchResults);
    }

}
