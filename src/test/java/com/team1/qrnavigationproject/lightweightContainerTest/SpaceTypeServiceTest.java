package com.team1.qrnavigationproject.lightweightContainerTest;

import com.team1.qrnavigationproject.model.SpaceType;
import com.team1.qrnavigationproject.repository.SpaceTypeRepo;
import com.team1.qrnavigationproject.service.SpaceTypeServiceImpl;
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
public class SpaceTypeServiceTest {

    @Mock
    private SpaceTypeRepo spaceTypeRepo;

    @InjectMocks
    private SpaceTypeServiceImpl spaceTypeService;

    private SpaceType spaceType;

    @BeforeEach
    public void init() {
        this.spaceType = TestData.createCountrySideTrail();
    }

    @DisplayName("Create a space type with test data")
    @Test
    public void testCreateSpaceType() {
        SpaceType spaceTypeToSave = this.spaceType;

        given(this.spaceTypeRepo.save(spaceTypeToSave)).willReturn(spaceTypeToSave);

        SpaceType resultSpaceType = this.spaceTypeService.saveSpaceType(spaceTypeToSave);

        verify(this.spaceTypeRepo, times(1)).save(spaceTypeToSave);

        assertNotNull(resultSpaceType);
        assertEquals(spaceTypeToSave, resultSpaceType);
    }

    @DisplayName("Get all space types available in the table")
    @Test
    public void testGetAllSpaceTypes() {
        List<SpaceType> spaceTypesToFetch = new ArrayList<>();
        spaceTypesToFetch.add(this.spaceType);

        given(this.spaceTypeRepo.findAll()).willReturn(spaceTypesToFetch);

        List<SpaceType> resultSpaceTypes = this.spaceTypeService.getAllSpaceTypes();

        verify(this.spaceTypeRepo, times(1)).findAll();

        assertNotNull(resultSpaceTypes);
        assertFalse(resultSpaceTypes.isEmpty());
    }

    @DisplayName("Fetch space type by using id number")
    @Test
    public void testFetchSpaceTypeByIdNumber() {
        SpaceType spaceTypeToFetch = this.spaceType;

        given(this.spaceTypeRepo.findById(spaceTypeToFetch.getId())).willReturn(Optional.of(spaceTypeToFetch));

        Optional<SpaceType> spaceType = this.spaceTypeService.getSpaceTypeById(spaceTypeToFetch.getId());

        verify(this.spaceTypeRepo, times(1)).findById(spaceTypeToFetch.getId());

        assertTrue(spaceType.isPresent());
        assertNotNull(spaceType.get());
    }

    @DisplayName("Delete space type by using id number")
    @Test
    public void testDeleteSpaceTypeByIdNumber() {
        SpaceType spaceTypeToDelete = this.spaceType;

        this.spaceTypeService.deleteSpaceType(spaceTypeToDelete.getId());

        verify(this.spaceTypeRepo, times(1)).deleteById(spaceTypeToDelete.getId());
    }
}

