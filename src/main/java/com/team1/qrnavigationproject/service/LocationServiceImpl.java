package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Location;
import com.team1.qrnavigationproject.repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepo locationRepo;

    @Autowired
    public LocationServiceImpl(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    @Override
    public Optional<Location> getLocationById(int id) {
        return locationRepo.findById(id);
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepo.save(location);
    }

    @Override
    public void deleteLocation(int id) {
        locationRepo.deleteById(id);
    }

    @Override
    public Location updateLocation(Location location) {
        return locationRepo.save(location);
    }

    @Override
    public List<Location> findLocationByOrganizationId(int organizationId) {
        return locationRepo.findLocationByOrganizationId(organizationId);
    }
}