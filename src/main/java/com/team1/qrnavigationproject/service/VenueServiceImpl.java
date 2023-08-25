package com.team1.qrnavigationproject.service;


import com.team1.qrnavigationproject.repository.VenueRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class VenueServiceImpl implements VenueService {
    private final VenueRepo venueRepo;

    @Autowired
    public VenueServiceImpl(VenueRepo venueRepo) {
        this.venueRepo = venueRepo;
    }
}
