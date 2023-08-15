package com.team1.qrnavigationproject.lightweightContainerTest;

import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.repository.SpaceRepo;
import com.team1.qrnavigationproject.service.SpaceServiceImpl;
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
public class SpaceServiceTest {

    @Mock
    private SpaceRepo spaceRepo;

    @InjectMocks
    private SpaceServiceImpl spaceService;

    private Space space;

    @BeforeEach
    public void init() {
        this.space = TestData.createSpace();
    }

    @DisplayName("Create a space with test data")
    @Test
    public void testCreateSpace() {
        Space spaceToSave = this.space;

        given(this.spaceRepo.save(spaceToSave)).willReturn(spaceToSave);

        Space resultSpace = this.spaceService.saveSpace(spaceToSave);

        verify(this.spaceRepo, times(1)).save(spaceToSave);

        assertNotNull(resultSpace);
        assertEquals(spaceToSave, resultSpace);
    }

    @DisplayName("Get all spaces available in the table")
    @Test
    public void testGetAllSpaces() {
        List<Space> spacesToFetch = new ArrayList<>();
        spacesToFetch.add(this.space);

        given(this.spaceRepo.findAll()).willReturn(spacesToFetch);

        List<Space> resultSpaces = this.spaceService.getAllSpaces();

        verify(this.spaceRepo, times(1)).findAll();

        assertNotNull(resultSpaces);
        assertFalse(resultSpaces.isEmpty());
    }

    @DisplayName("Fetch space by using id number")
    @Test
    public void testFetchSpaceByIdNumber() {
        Space spaceToFetch = this.space;

        given(this.spaceRepo.findById(spaceToFetch.getId())).willReturn(Optional.of(spaceToFetch));

        Optional<Space> space = this.spaceService.getSpaceById(spaceToFetch.getId());

        verify(this.spaceRepo, times(1)).findById(spaceToFetch.getId());

        assertTrue(space.isPresent());
        assertNotNull(space.get());
    }

    @DisplayName("Delete space by using id number")
    @Test
    public void testDeleteSpaceByIdNumber() {
        Space spaceToDelete = this.space;

        this.spaceService.deleteSpace(spaceToDelete.getId());

        verify(this.spaceRepo, times(1)).deleteById(spaceToDelete.getId());
    }
}

