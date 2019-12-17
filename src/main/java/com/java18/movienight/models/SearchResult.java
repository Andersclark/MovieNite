package com.java18.movienight.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult implements Serializable {

    @Id
    ObjectId _id;
    public String searchString;

    @JsonProperty("Search")
    private ArrayList<MoviePreview> movies;
    @JsonProperty("totalResults")
    private int totalResults;
    public ArrayList<MoviePreview> getMovies() {
        return movies;
    }
    public int getTotalResults(){
        return totalResults;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString.trim();
    }
}
