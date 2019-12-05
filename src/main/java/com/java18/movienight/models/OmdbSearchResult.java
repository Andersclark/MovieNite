package com.java18.movienight.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class OmdbSearchResult {

    @JsonProperty("Search")
    private ArrayList<MoviePreview> movies;

    public OmdbSearchResult() {
        movies = new ArrayList<>();
    }

    public ArrayList<MoviePreview> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MoviePreview> movies) {
        this.movies = movies;
    }
}
