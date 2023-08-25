package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {

//    SELECT l.id, l.latitude, l.longitude, s.org_id FROM c22106964_qrnavigation.location
//    l left join
//    c22106964_qrnavigation.address a on l.id=a.id
//    left join c22106964_qrnavigation.space s on a.id=s.id where org_id=3;

    @Query("SELECT l FROM Location l JOIN Address a on l.id=a.id JOIN Space s on a.id=s.id WHERE s.organization.id= :orgId")
    List<Location> findLocationByOrganizationId(int orgId);


}