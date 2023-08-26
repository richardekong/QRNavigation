package com.team1.qrnavigationproject.lightweightContainerTest;

import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.repository.SubSpaceRepo;
import com.team1.qrnavigationproject.service.SubSpaceServiceImpl;
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
public class SubSpaceServiceTest {

    @Mock
    private SubSpaceRepo subSpaceRepo;

    @InjectMocks
    private SubSpaceServiceImpl subSpaceService;

    private SubSpace subSpace;

    @BeforeEach
    public void init() {
        this.subSpace = TestData.createSubSpace();
    }

    @DisplayName("Create a sub space with test data")
    @Test
    public void testCreateSubSpace() {
        SubSpace subSpaceToSave = this.subSpace;

        given(this.subSpaceRepo.save(subSpaceToSave)).willReturn(subSpaceToSave);

        SubSpace resultSubSpace = this.subSpaceService.saveSubSpace(subSpaceToSave);

        verify(this.subSpaceRepo, times(1)).save(subSpaceToSave);

        assertNotNull(resultSubSpace);
        assertEquals(subSpaceToSave, resultSubSpace);
    }

    @DisplayName("Get all sub spaces available in the table")
    @Test
    public void testGetAllSubSpaces() {
        List<SubSpace> subSpacesToFetch = new ArrayList<>();
        subSpacesToFetch.add(this.subSpace);

        given(this.subSpaceRepo.findAll()).willReturn(subSpacesToFetch);

        List<SubSpace> resultSubSpaces = this.subSpaceService.getAllSubSpaces();

        verify(this.subSpaceRepo, times(1)).findAll();

        assertNotNull(resultSubSpaces);
        assertFalse(resultSubSpaces.isEmpty());
    }

    @DisplayName("Fetch sub space by using id number")
    @Test
    public void testFetchSubSpaceByIdNumber() {
        SubSpace subSpaceToFetch = this.subSpace;

        given(this.subSpaceRepo.findById(subSpaceToFetch.getId())).willReturn(subSpaceToFetch);

        Optional<SubSpace> subSpace = this.subSpaceService.getSubSpaceById(subSpaceToFetch.getId());

        verify(this.subSpaceRepo, times(1)).findById(subSpaceToFetch.getId());

        assertTrue(subSpace.isPresent());
        assertNotNull(subSpace.get());
    }

    @DisplayName("Delete sub space by using id number")
    @Test
    public void testDeleteSubSpaceByIdNumber() {
        SubSpace subSpaceToDelete = this.subSpace;

        this.subSpaceService.deleteSubSpace(subSpaceToDelete.getId());

        verify(this.subSpaceRepo, times(1)).deleteById(subSpaceToDelete.getId());
    }
}

