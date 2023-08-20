package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.SubSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubSpaceRepo extends JpaRepository<SubSpace, Integer>{
    Optional<SubSpace> findSubSpaceByName(String subSpaceName);
    @Query("SELECT s FROM SubSpace s WHERE s.space.id = :spaceId")
    List<SubSpace> getSubspacesBySpaceId(Integer spaceId);
    SubSpace findById(int id);
//    @Query("SELECT ss.space.name, ss.space.id, ss.name, ss.id FROM SubSpace ss WHERE ss.space.organization.id = :orgId ORDER BY ss.space.name, ss.name")
//    List<Object[]> getSpaceAndSubspaceInfo(int orgId);


}