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

    @Query("SELECT s From Space s")
    List<Space> findAll();

}