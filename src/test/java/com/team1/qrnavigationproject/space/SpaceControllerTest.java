package com.team1.qrnavigationproject.space;


import com.team1.qrnavigationproject.controller.SpaceController;
import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.service.OrganizationService;
import com.team1.qrnavigationproject.service.SpaceService;
import com.team1.qrnavigationproject.service.SpaceTypeService;
import com.team1.qrnavigationproject.service.UserService;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.*;

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

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewCreateMainPlacePage() {
        List<SpaceType> mockSpaceTypes = new ArrayList<>();
        /* create mock SpaceType list */
        ;
        SpaceType spaceType = new SpaceType(1, "test");
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

//    @Test
//    void testCreateSubPlaceForm() {
//        Map<String, String> params = Map.of("spaceIdParam", "1", "typeIdParam", "2");
//        int spaceId = Integer.parseInt(params.get("spaceIdParam"));
//        int typeId = Integer.parseInt(params.get("typeIdParam"));
//        SubSpace mockSubSpace = TestData.createSubSpace();
//        Space mockSpace = TestData.createSpace();
//        SpaceType mockSpaceType = new SpaceType(1, "Building");
//        mockSpace.add(mockSubSpace);
//        mockSpace.setTypeId(mockSpaceType.getId());
//        User mockUser = TestData.createDavid();
//        Organization mockOrganization = new Organization();
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(mockUser.getPassword(), mockUser.getUsername());
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        if (auth.getName() != null && auth.isAuthenticated()){
////            when(auth.getName()).thenReturn(mockUser.getUsername());
////        }
//        when(spaceService.getSpaceById(spaceId)).thenReturn(Optional.of(mockSpace));
//        when(spaceTypeService.getSpaceTypeById(typeId)).thenReturn(Optional.of(mockSpaceType));
//        when(spaceService.updateSpace(mockSpace)).thenReturn(mockSpace);
//        when(organizationService.update(mockOrganization)).thenReturn(mockOrganization);
//        when(userService.findUserByUsername(auth.getName())).thenReturn(Optional.of(mockUser));
//
//        ModelAndView mockMav = mock(ModelAndView.class);
//        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//        ModelAndView resultMav = spaceController.createSubPlaceForm(params.get("spaceIdParam"), params.get("typeIdParam"), mockSubSpace, mockMav, redirectAttributes, auth);
//
//        verify(spaceService).getSpaceById(spaceId);
//        verify(spaceTypeService).getSpaceTypeById(typeId);
////        verify(userService).findUserByUsername(mockUser.getUsername());
//        verify(spaceService).updateSpace(mockSpace);
//        verify(organizationService).update(mockOrganization);
//        verify(mockMav).addObject("space", mockSpace);
//        verify(mockMav).addObject("success", "Success (201): Space created");
//        verify(mockMav).setViewName("redirect:/admin/places");
//
//        assertEquals(mockMav, resultMav);
//    }


    /*
    * This functionality can't be tested easily with using the light-weight container test.
    * Transferring this test to FullSpaceContainer test
    * */

//    @Test
//    public void testDeleteSpace() {
//        int spaceId = 9; // Replace with a valid space ID
//        // Perform the delete operation
//        String result = spaceController.deleteSpace(spaceId, mock(Authentication.class) ,mock(RedirectAttributes.class));
//
//        verify(spaceService).deleteSpace(spaceId);
//
//        // Verify that the result is the expected redirect string
//        assertEquals("redirect:/admin/places", result);
//
//    }

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

