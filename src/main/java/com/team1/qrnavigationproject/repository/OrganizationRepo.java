package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepo extends JpaRepository<Organization, Integer> {

    Optional<Organization> findOrganizationById(int id);

    @Query("SELECT o FROM Organization o WHERE o.name = :name")
    Optional<Organization> findOrganizationByName(String name);
    Optional<Organization> findOrganizationByAddressId(int id);

    @Query("SELECT s FROM Organization o JOIN o.spaces s WHERE o.id = :id")
    Optional<List<Space>> findAllSpacesById(int id);

    @Query("SELECT e FROM Organization o JOIN o.events e WHERE o.id = :id")
    Optional<List<Event>> findAllEventsById(int id);


}
