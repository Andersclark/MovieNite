package com.java18.movienight.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqEvent implements Serializable {
    public String _id;
    public String movieId;
    public String movie;
    public String movieURL;
    public String organizerId;
    public String[] guestIds;
    public int duration;
    public long startTime;
    public String description;
    public String location;
}