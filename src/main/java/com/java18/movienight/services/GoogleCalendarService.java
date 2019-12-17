package com.java18.movienight.services;

import com.java18.movienight.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GoogleCalendarService {
  @Autowired
  OAuthService oAuthService;
  @Autowired
  UserService userService;

  public String getEvents() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByEmail(email);
    long now = Instant.now().toEpochMilli();
    // refresh access token if it expires within 30 second
    if(user.getTokenExpires() + 1800000 < now) {
      oAuthService.refreshAccessToken(user);
    }

    return "OK";
  }
}
