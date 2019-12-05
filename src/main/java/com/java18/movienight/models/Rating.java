package com.java18.movienight.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Serializable {
    @JsonProperty("Source")
    public String source;
    @JsonProperty("Rating")
    public String rating;
}


/*  OMDB-response:
{
    "Source": "Internet Movie Database",
    "Value": "8.2/10"
}
*/

