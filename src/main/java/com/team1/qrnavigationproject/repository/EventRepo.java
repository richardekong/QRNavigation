package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.organizer.id = :organizationId")
    List<Event> findAllEvents(int organizationId);
    Event save(Event event);
    @Query("SELECT e FROM Event e WHERE e.name = :eventName")
    Event findByEventName(@Param("eventName") String eventName);
    @Query("SELECT e FROM Event e WHERE e.id = :eventId")
    Event findByEventId(@Param("eventId") int eventId);
    @Query("SELECT e.id as eventId, e.name as eventName FROM Event e WHERE e.id IN :eventIds")
    List<Object[]> findEventNamesAndIdsByIds(@Param("eventIds") List<Integer> eventIds);
}

