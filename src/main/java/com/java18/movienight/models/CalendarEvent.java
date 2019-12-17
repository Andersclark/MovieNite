package com.java18.movienight.models;

import java.time.LocalDateTime;

public class CalendarEvent {
  public long start;
  public long end;

  public CalendarEvent(long start, long end) {
    this.start = start;
    this.end = end;
  }
}
