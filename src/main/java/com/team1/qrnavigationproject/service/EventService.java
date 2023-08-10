package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Event;

import java.util.List;


public interface EventService {
     List<Event> findAll();
     Event saveEvent(Event event);
     Event findEventByName(String eventName);
     Event findEventById(int eventId);
     Event updateEvent(Event event);
     void deleteEventById(int eventId);
     List<Object[]> findEventNamesAndIdsByIds(List<Integer> eventIds);
}
