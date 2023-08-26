package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Venue;

import java.util.List;

public interface VenueService {
    Venue save(Venue venue);
    List<Venue> findAllByEventVenuesByEventId(int eventId);
}
