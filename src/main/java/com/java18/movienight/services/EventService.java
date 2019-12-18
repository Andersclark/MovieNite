package com.java18.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.java18.movienight.entities.User;
import com.java18.movienight.models.ReqEvent;
import com.java18.movienight.repositories.EventRepo;
import com.java18.movienight.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
  @Autowired
  OAuthService oAuthService;
  @Autowired
  UserRepo userRepo;

  @Autowired
  private EventRepo eventRepo;

  public List<ReqEvent> findByMovieId(String movieId) {
    return eventRepo.findByMovieId(movieId);
  }

  public Optional<ReqEvent> findById(String eventId) {
    return eventRepo.findById(eventId);
  }

  public List<ReqEvent> findByOrganizerId(String organizerId) {
    return eventRepo.findByOrganizerId(organizerId);
  }

  @SuppressWarnings("deprecation")
  public void insertEvent(ReqEvent reqEvent) {
    List<User> users = userRepo.findAll();

    User user = userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    oAuthService.refreshAccessToken(user);
    GoogleCredential credential = oAuthService.getRefreshedCredentials(user.getRefreshToken());
    Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
            .setApplicationName("Movie Knights")
            .build();
    saveEvent(calendar, reqEvent, users);
  }

  private void saveEvent(Calendar calendar, ReqEvent reqEvent, List<User> users) {
    Event event = new Event();
    int timeOffset = ZonedDateTime.now().getOffset().getTotalSeconds() * 1000;
    DateTime startDateTime = new DateTime(reqEvent.startTime - timeOffset);
    DateTime endDateTime = new DateTime((reqEvent.startTime - timeOffset) + reqEvent.duration * 60000);
    EventDateTime start = new EventDateTime().setDateTime(startDateTime);
    EventDateTime end = new EventDateTime().setDateTime(endDateTime);

    event.setSummary("Movie knight - " + reqEvent.movie);
    event.setDescription(reqEvent.description + "\n\n" + reqEvent.movieURL);
    event.setLocation(reqEvent.location);
    event.setStart(start);
    event.setEnd(end);

    List<EventAttendee> attendees = users.stream()
            .map(user -> new EventAttendee()
                    .setDisplayName(user.getUsername())
                    .setEmail(user.getEmail())
                    .setResponseStatus("accepted")
                    .setSelf(user.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())))
                    .collect(Collectors.toList());

    event.setAttendees(attendees);

    EventReminder[] reminder = new EventReminder[]{
            new EventReminder().setMethod("email").setMinutes(1440),
            new EventReminder().setMethod("popup").setMinutes(30),
    };
    Event.Reminders reminders = new Event.Reminders()
            .setUseDefault(false)
            .setOverrides(Arrays.asList(reminder));
    event.setReminders(reminders);

    try {
      calendar.events().insert("primary", event).execute();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed saving event into calendar");
    }
  }
}
