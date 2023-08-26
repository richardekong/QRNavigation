package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepo extends JpaRepository<Venue, Integer> {

    @Query("SELECT v FROM Venue v WHERE v.event.id = :eventId ORDER BY v.spaceId")
    List<Venue> findAllByEventVenuesByEventId(int eventId);
}
