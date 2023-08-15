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
}