package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Event;

import java.time.LocalDateTime;
import java.util.List;
public interface EventService {
     List<Event> findAll(int organizationId);
     Event saveEvent(Event event);
     Event findEventByName(String eventName);
     Event findEventById(int eventId);
     Event updateEvent(Event event);
     void deleteEventById(int eventId);
     List<Object[]> findEventNamesAndIdsByIds(List<Integer> eventIds);

     List<Event> findEventsWithin2Days(int subspaceId);

}
