package com.team1.qrnavigationproject.space;


import com.team1.qrnavigationproject.controller.SpaceController;
import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.service.OrganizationService;
import com.team1.qrnavigationproject.service.SpaceService;
import com.team1.qrnavigationproject.service.SpaceTypeService;
import com.team1.qrnavigationproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpaceControllerTest {

    @InjectMocks
    private SpaceController spaceController;

    @Mock
    private SpaceService spaceService;

    @Mock
    private SpaceTypeService spaceTypeService;

    @Mock
    private UserService userService;

    @Mock
    private OrganizationService organizationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testViewCreateMainPlacePage() {
        List<SpaceType> mockSpaceTypes = new ArrayList<>();
        /* create mock SpaceType list */;
        SpaceType spaceType = new SpaceType(1,"test");
        mockSpaceTypes.add(spaceType);
        when(spaceTypeService.getAllSpaceTypes()).thenReturn(mockSpaceTypes);
        ModelAndView mockMav = mock(ModelAndView.class);
        when(mockMav.addObject(anyString(), any())).thenReturn(mockMav);

        // Stub the void method using doNothing
        doNothing().when(mockMav).setViewName("createMainPlace");

        ModelAndView resultMav = spaceController.viewCreateMainPlacePage(mockMav);

        verify(spaceTypeService).getAllSpaceTypes();
        verify(mockMav).addObject("spaceTypes", mockSpaceTypes);
        verify(mockMav).setViewName("createMainPlace");
        assertEquals(mockMav, resultMav);
    }

    @Test
    void testCreateSubPlaceForm() {
        int spaceId = 1;
        int typeId = 2;
        SubSpace mockSubSpace = new SubSpace();
        Space mockSpace = new Space();
        SpaceType mockSpaceType = new SpaceType();
        User mockUser = new User();
        Organization mockOrganization = new Organization();

        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");

        when(spaceService.getSpaceById(spaceId)).thenReturn(Optional.of(mockSpace));
        when(spaceTypeService.getSpaceTypeById(typeId)).thenReturn(Optional.of(mockSpaceType));
        when(userService.findUserByUsername(auth.getName())).thenReturn(Optional.of(mockUser));
        when(spaceService.updateSpace(mockSpace)).thenReturn(mockSpace);
        when(organizationService.update(mockOrganization)).thenReturn(mockOrganization);

        ModelAndView mockMav = mock(ModelAndView.class);

        ModelAndView resultMav = spaceController.createSubPlaceForm(spaceId, typeId, mockSubSpace, mockMav, auth);

        verify(spaceService).getSpaceById(spaceId);
        verify(spaceTypeService).getSpaceTypeById(typeId);
        verify(userService).findUserByUsername(auth.getName());
        verify(spaceService).updateSpace(mockSpace);
        verify(organizationService).update(mockOrganization);
        verify(mockMav).addObject("space", mockSpace);
        verify(mockMav).addObject("success", "Success (201): Space created");
        verify(mockMav).setViewName("redirect:/admin/places");

        assertEquals(mockMav, resultMav);
    }


    @Test
    public void testDeleteSpace() {
        int spaceId = 9; // Replace with a valid space ID

        // Perform the delete operation
        String result = spaceController.deleteSpace(spaceId);

        verify(spaceService).deleteSpace(spaceId);

        // Verify that the result is the expected redirect string
        assertEquals("redirect:/admin/places", result);
    }

    @Test
    void testEditPlaceForm() {
        Space mockSpace = new Space();
        mockSpace.setId(1);

        when(spaceService.updateSpace(any())).thenReturn(mockSpace);

        ModelAndView mockMav = mock(ModelAndView.class);

        ModelAndView resultMav = spaceController.editPlaceForm(mockSpace, mockMav);

        verify(spaceService).updateSpace(mockSpace);
        verify(mockMav).addObject("success", "Changes saved!");
        verify(mockMav).setViewName("redirect:/admin/places");

        assertEquals(mockMav, resultMav);
    }
}

