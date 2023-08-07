package com.team1.qrnavigationproject.lightweight;

import com.team1.qrnavigationproject.model.Location;
import com.team1.qrnavigationproject.repository.LocationRepo;
import com.team1.qrnavigationproject.service.LocationServiceImpl;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepo locationRepo;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;

    @BeforeEach
    public void init() {
        this.location = TestData.createLocation();
    }

    @DisplayName("Create a location with test data")
    @Test
    public void testCreateLocation() {
        Location locationToSave = this.location;

        given(this.locationRepo.save(locationToSave)).willReturn(locationToSave);

        Location resultLocation = this.locationService.saveLocation(locationToSave);

        verify(this.locationRepo, times(1)).save(locationToSave);

        assertNotNull(resultLocation);
        assertEquals(locationToSave, resultLocation);
    }

    @DisplayName("Get all locations available in the table")
    @Test
    public void testGetAllLocations() {
        List<Location> locationsToFetch = new ArrayList<>();
        locationsToFetch.add(this.location);

        given(this.locationRepo.findAll()).willReturn(locationsToFetch);

        List<Location> resultLocations = this.locationService.getAllLocations();

        verify(this.locationRepo, times(1)).findAll();

        assertNotNull(resultLocations);
        assertFalse(resultLocations.isEmpty());
    }

    @DisplayName("Fetch location by using id number")
    @Test
    public void testFetchLocationByIdNumber() {
        Location locationToFetch = this.location;

        given(this.locationRepo.findById(locationToFetch.getId())).willReturn(Optional.of(locationToFetch));

        Optional<Location> location = this.locationService.getLocationById(locationToFetch.getId());

        verify(this.locationRepo, times(1)).findById(locationToFetch.getId());

        assertTrue(location.isPresent());
        assertNotNull(location.get());
    }

    @DisplayName("Delete location by using id number")
    @Test
    public void testDeleteLocationByIdNumber() {
        Location locationToDelete = this.location;

        this.locationService.deleteLocation(locationToDelete.getId());

        verify(this.locationRepo, times(1)).deleteById(locationToDelete.getId());
    }
}
