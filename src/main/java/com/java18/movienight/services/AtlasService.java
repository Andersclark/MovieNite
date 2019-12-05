package com.java18.movienight.services;

import com.java18.movienight.models.Movie;
import com.java18.movienight.models.MoviePreview;
import com.java18.movienight.repositories.AtlasMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtlasService {

    @Autowired
    AtlasMovieRepository collection;

    public AtlasService() {  }

    public Optional<Movie> findById(String id){
        return collection.findById(id);
    }
    public List<Movie> findByTitleContaining(String title){
        return collection.findByTitleContaining(title);
    }

}
