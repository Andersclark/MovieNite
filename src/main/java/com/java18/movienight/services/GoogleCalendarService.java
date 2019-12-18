package com.java18.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.java18.movienight.entities.User;
import com.java18.movienight.models.CalendarEvent;
import com.java18.movienight.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleCalendarService {
  @Autowired
  OAuthService oAuthService;
  @Autowired
  UserRepo userRepo;

  @SuppressWarnings("deprecation")
  public List<Long> getEvents(int duration) {
    List<User> allUsers = userRepo.findAll();
    List<CalendarEvent> allEvents = new ArrayList<>();

    for (User user : allUsers) {
      GoogleCredential credential = oAuthService.getRefreshedCredentials(user.getRefreshToken());
      oAuthService.refreshAccessToken(user);

      Calendar calendar =
              new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                      .setApplicationName("Movie Knights")
                      .build();

      DateTime now = new DateTime(System.currentTimeMillis());
      Events events = null;
      try {
        events = calendar.events().list("primary")
                .setMaxResults(20)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
      } catch (IOException e) {
        e.printStackTrace();
      }

      List<Event> items = events.getItems();

      if (items.isEmpty()) {
        System.out.println("No upcoming events found.");
      } else {
        for (Event event : items) {
          DateTime start = event.getStart().getDateTime();
          if (start == null) { // If it's an all-day-event - store the date instead
            start = event.getStart().getDate();
          }
          DateTime end = event.getEnd().getDateTime();
          if (end == null) { // If it's an all-day-event - store the date instead
            end = event.getStart().getDate();
          }
          allEvents.add(new CalendarEvent(start.getValue(), end.getValue()));
        }
      }
    }
   return filterEvents(duration, allEvents);
  }

  private List<Long> filterEvents(int duration, List<CalendarEvent> allEvents) {
    List<Long> responseEvents = new ArrayList<>();
    long movieLength = duration * 60000;
    LocalDateTime today = LocalDateTime.now();

    // loop 14 days, 7 hours a day
    for (int day = 0; day < 14; day++) {
      for (int hour = 16; hour < 24; hour++) {
        LocalDateTime now = LocalDateTime.of(LocalDate.of(
                today.getYear(),
                today.getMonth(),
                today.getDayOfMonth() + day
        ), LocalTime.of(hour, 0));

        long movieStart = Timestamp.valueOf(now).getTime();
        long movieEnd = movieStart + movieLength;

        List<CalendarEvent> todaysEvents = allEvents.stream()
                .filter(event -> LocalDateTime.ofInstant(Instant.ofEpochMilli(event.start),
                        ZoneId.systemDefault()).getDayOfYear() == now.getDayOfYear())
                .collect(Collectors.toList());

        long eventsStart = 0, eventsEnd = 0;
        // collect all events that day
        // to minimum start time and maximum end time
        for (CalendarEvent event : todaysEvents) {
          if (event.start < eventsStart) eventsStart = event.start;
          if (event.end > eventsEnd) eventsEnd = event.end;
        }

        // check if movie collides with collected events
        if (eventsStart - 1800000 > movieEnd || eventsEnd + 1800000 < movieStart) {
          // free on this date and hour for all users
          responseEvents.add(movieStart);
        }
      }
    }
    return responseEvents;
  }
}
