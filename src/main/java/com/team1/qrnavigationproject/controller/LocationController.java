package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.Location;
import com.team1.qrnavigationproject.service.LocationService;
import com.team1.qrnavigationproject.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/qrnavigation/locations")
public class LocationController {

    private final LocationService locationService;

    private OrganizationService organizationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable int id) {
        return locationService.getLocationById(id)
                .map(location -> new ResponseEntity<>(location, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{organizationName}")
    public ResponseEntity<List<Location>> getLocationsByOrganization(@PathVariable String organizationName) {
        List<Location> locations = locationService.findLocationByOrganizationId(organizationService.findByName(organizationName).getId());
        if (locations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location savedLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable int id, @RequestBody Location location) {
        return locationService.getLocationById(id)
                .map(existingLocation -> {
                    location.setId(id);
                    Location updatedLocation = locationService.saveLocation(location);
                    return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable int id) {
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}