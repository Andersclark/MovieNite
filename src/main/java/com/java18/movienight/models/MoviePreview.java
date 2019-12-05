package com.java18.movienight.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class MoviePreview implements Serializable {

    public String title;
    public String year;
    @Id
    public String imdbId;
    public String type;
    public String poster;

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
