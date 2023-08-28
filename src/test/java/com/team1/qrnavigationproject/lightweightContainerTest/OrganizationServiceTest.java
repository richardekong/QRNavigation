package com.team1.qrnavigationproject.lightweightContainerTest;


import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.repository.OrganizationRepo;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.service.OrganizationServiceImpl;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest {

    @Mock
    OrganizationRepo organizationRepo;

    @InjectMocks
    OrganizationServiceImpl organizationService;

    private Organization organization;


    @BeforeEach
    public void init() {
        organization = TestData.createOrganization();
    }

    /**
     * A lightweight test that verifies the organization service's
     * ability to save a new organization's record to the database.
     **/
    @DisplayName("Verify if organization can be saved")
    @Test
    void canSave(){
        Organization orgToSave = organization;
        //determine if the save operation will create a new organization's record in the database
        given(organizationRepo.save(orgToSave)).willReturn(orgToSave);
        //save the organization and maintain reference to the returned organization
        Organization returnedOrganization = organizationService.save(orgToSave);
        //The organization service should delegate the repository to save an organization in the database
        then(organizationRepo).should().save(orgToSave);
        assertThat(returnedOrganization).isNotNull();
    }

    /**
     * A lightweight test that verifies if a list of all organizations
     * can be retrieved
     * */
    @DisplayName("Verify if all organizations can be retrieved")
    @Test
    void canRetrieveAll(){
        given(organizationRepo.findAll()).willReturn(List.of(organization));
        //retrieve the organizations from the database and maintain a reference to the
        //retrieved organization
        List<Organization> allOrganization = organizationService.findAll();
        //delegate the repository to invoke its findAll(..) method
        then(organizationRepo).should().findAll();

        assertThat(allOrganization).isNotNull();
        assertThat(allOrganization.isEmpty()).isFalse();
        assertThat(allOrganization.size()).isGreaterThan(0);
    }

    /**
     * A lightweight test that verifies if an organization can be retrieved by its name
     * **/
    @DisplayName("Verify if all organization can be retrieved by its name")
    @Test
    void canBeRetrievedByName(){
        String name = organization.getName();

        given(organizationRepo.findOrganizationByName(name)).willReturn(Optional.of(organization));

        Organization retrievedOrganization = organizationService.findByName(name);

        then(organizationRepo).should().findOrganizationByName(name);

        assertThat(retrievedOrganization).isNotNull();

        assertThat(retrievedOrganization).isEqualTo(organization);

        assertThrows(CustomException.class, () -> organizationService.findByName("Fake_name"));

    }

    /**
     * A lightweight test that verifies if a list of spaces can be retrieved from an
     * organization based on its id
     * **/
    @DisplayName("Verify if all organization space can be retrieved by its id")
    @Test
    void canRetrieveAllLinkedSpaces(){
        List<Space> spaces;
        Space space = TestData.createSpace();
        given(organizationRepo.save(organization)).willReturn(organization);

        organization.add(space);

        spaces = organization.getSpaces();

        Organization savedOrg = organizationService.save(organization);

        given(organizationRepo.findAllSpacesById(savedOrg.getId())).willReturn(Optional.of(spaces));

        List <Space> retrieveSpaces  = organizationService.findAllSpacesById(savedOrg.getId());

        then(organizationRepo).should().findAllSpacesById(savedOrg.getId());

        assertThrows(CustomException.class, () -> organizationService.findAllSpacesById(-1));

        assertThat(retrieveSpaces).isNotNull();

        assertThat(retrieveSpaces.size()).isEqualTo(1);
    }

    /**
     * A lightweight test that verifies if a list of events can be retrieved from
     * an organization based on its id
     **/
    @DisplayName("Verify if all organization event can be retrieved by its id")
    @Test
    void canRetrieveAllScheduledEvents(){
        List<Event> events;
        Event event = TestData.createEvent();

        given(organizationRepo.save(organization)).willReturn(organization);

        organization.add(event);

        events = organization.getEvents();

        Organization savedOrg = organizationService.save(organization);

        given(organizationRepo.findAllEventsById(savedOrg.getId())).willReturn(Optional.of(events));

        List<Event> retrievedEvents = organizationService.findAllEventsById(savedOrg.getId());

        then(organizationRepo).should().findAllEventsById(savedOrg.getId());

        assertThat(retrievedEvents).isNotNull();

        assertThrows(CustomException.class, () -> organizationService.findAllEventsById(100));

        assertThat(retrievedEvents.size()).isEqualTo(1);

    }

    /**
     * A lightweight test that verifies if an organization can be updated
     **/
    @DisplayName("Verify if an organization can be updated")
    @Test
    void canBeUpdated(){
        String name = "Cardiff Met";
        var existingOrg = organization;
        //Mock the repository's behavior to determine if an organization exists
        when(organizationRepo.existsById(1)).thenReturn(true);

        //update the organization
        existingOrg.setName(name);
        organizationService.update(existingOrg);

        verify(organizationRepo, times(1)).existsById(1);
        verify(organizationRepo, times(1)).save(existingOrg);

    }

}
