package com.java18.movienight.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class SearchResult implements Serializable {

    @Id
    ObjectId _id;
    public String searchString;

    @JsonProperty("Search")
    private ArrayList<MoviePreview> movies;

    public SearchResult() {
        movies = new ArrayList<>();
    }

    public ArrayList<MoviePreview> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MoviePreview> movies) {
        this.movies = movies;
    }
}
