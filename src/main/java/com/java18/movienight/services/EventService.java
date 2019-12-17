package com.java18.movienight.services;

import com.java18.movienight.models.Event;
import com.java18.movienight.repositories.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

        @Autowired
        private EventRepo eventRepo;

        public List<Event> findByMovieId(String movieId) {
            return eventRepo.findByMovieId(movieId);
        }
        public Optional<Event> findById(String eventId) {
            return eventRepo.findById(eventId);
        }
        public List<Event> findByOrganizerId(String organizerId) {
            return eventRepo.findByOrganizerId(organizerId);
        }
        public Event insertEvent(Event event) {
            return eventRepo.save(event);
        }
        public void deleteEvent(Event event) {
            eventRepo.delete(event);
        }


}
