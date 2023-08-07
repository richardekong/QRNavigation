package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.Space;

import java.util.List;
import java.util.Map;

public interface OrganizationService {
    Organization save(Organization organization);
    Organization findById(int id);
    Organization findByName(String name);

    List<Organization> findAll();
    List<Space> findAllSpacesById(int id);

    List<Event> findAllEventsById(int id);

    Organization update(Organization organization);


}
