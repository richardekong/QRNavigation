package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepo extends JpaRepository<Venue, Integer> {
}
