package com.java18.movienight.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class MoviePreview {

    public String title;
    public String year;
    @Id public String imdbId;
    public String type;
    public String poster;

    public MoviePreview() {}

    public MoviePreview(String title, String year, String imdbId, String type, String poster) {
        this.title = title;
        this.year = year;
        this.imdbId = imdbId;
        this.type = type;
        this.poster = poster;
    }
}

/*  ACTUAL OMDB-RESPONSE:

{
    "Search": [
        {
            "Title": "Bat*21",
            "Year": "1988",
            "imdbID": "tt0094712",
            "Type": "movie",
            "Poster": "https://m.media-amazon.com/images/M/MV5BYzgyMDVlMGUtZDAzNy00MmM3LWE5MDgtNzkxOGMyYTkxNDlhL2ltYWdlXkEyXkFqcGdeQXVyNjQzNDI3NzY@._V1_SX300.jpg"
        },
        {
            "Title": "The Bat",
            "Year": "1959",
            "imdbID": "tt0052602",
            "Type": "movie",
            "Poster": "https://m.media-amazon.com/images/M/MV5BYWY3N2M0MzktZjkxNi00MjNlLTg2ZjctZGVjZTZhNzZiMDc4XkEyXkFqcGdeQXVyMDI2NDg0NQ@@._V1_SX300.jpg"
        }
    ]
}

 */
