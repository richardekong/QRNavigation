package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    public List<Location> getAllLocations();
    public Optional<Location> getLocationById(int id);
    public Location saveLocation(Location location);
    public void deleteLocation(int id);
    Location updateLocation(Location location);

    List <Location> findLocationByOrganizationId(int organizationId);

}
