package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Venue;
import com.team1.qrnavigationproject.repository.VenueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueRepo venueRepo;

    @Autowired
    public VenueServiceImpl(VenueRepo venueRepo) {
        this.venueRepo = venueRepo;
    }

    @Override
    public Venue save(Venue venue) {
        return venueRepo.save(venue);
    }
    @Override
    public List<Venue> findAllByEventVenuesByEventId(int eventId){ return venueRepo.findAllByEventVenuesByEventId(eventId); }

}
