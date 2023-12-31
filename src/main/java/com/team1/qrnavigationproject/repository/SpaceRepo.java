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

    @Query("SELECT space FROM Space space JOIN SubSpace subspace on space.id=subspace.space.id WHERE space.name= :spaceName and subspace.name= :subspaceName")
    Space findByNameAndSubspaceName(String spaceName, String subspaceName);

    Space findById(int id);
    @Query("SELECT s FROM Space s WHERE s.organization.id = :organizationId")
    List<Space> findAllSpaces(int organizationId);

}