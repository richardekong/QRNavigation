package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepo extends JpaRepository<Space, Integer> {
    Optional<Space> findSpaceByName(String spaceName);
    Space findByName(String spaceName);
    Space findById(int id);
    @Query("SELECT s FROM Space s WHERE s.organization.id = :organizationId")
    List<Space> findAllSpaces(int organizationId);
    @Query("SELECT s FROM Space s WHERE s.event.id = :eventId")
    List<Space> findAllSpacesByEvent(int eventId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Space s WHERE s.event.id = :eventId AND s.id = :spaceId")
    boolean isSpaceIncludedInEvent(int eventId, int spaceId);
}