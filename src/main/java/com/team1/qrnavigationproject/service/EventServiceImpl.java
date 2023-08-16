package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    private EventRepo eventRepo;

    @Autowired
    void setEventRepo(EventRepo eventRepo){

        this.eventRepo = eventRepo;
    }
    @Override
    public List<Event> findAll(int organizationId){
        return eventRepo.findAllEvents(organizationId);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepo.save(event);
    }

    @Override
    public Event findEventByName(String eventName) {
        return eventRepo.findByEventName(eventName);
    }
    @Override
    public Event findEventById(int eventId) {
        return eventRepo.findByEventId(eventId);
    }
    @Override
    public void deleteEventById(int eventId) {
        Event event = eventRepo.findByEventId(eventId);
        if (event != null) {
            eventRepo.delete(event);
        }
    }

    @Override
    public Event updateEvent(Event event) {
        Event existingEvent = eventRepo.findByEventId(event.getId());
        if (existingEvent != null) {
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setStart(event.getStart());
            existingEvent.setEnd(event.getEnd());
            return eventRepo.save(existingEvent);
        } else {
            throw new IllegalArgumentException("Event with name " + event.getName() + " not found." + existingEvent.getName());
        }
    }

    @Override
    public List<Object[]> findEventNamesAndIdsByIds(List<Integer> eventIds) {
        return eventRepo.findEventNamesAndIdsByIds(eventIds);
    }
}
