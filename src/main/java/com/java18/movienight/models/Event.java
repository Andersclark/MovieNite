package com.java18.movienight.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java18.movienight.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {


    public String _id;
    public String movieId;
    public String organizerId;
    public User organizer;
    public String[] guestIds;
    public int duration;
    public Date startTime;
    public String description;
    public String location;


}