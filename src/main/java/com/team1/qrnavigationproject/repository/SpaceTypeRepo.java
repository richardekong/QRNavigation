package com.team1.qrnavigationproject.repository;
import com.team1.qrnavigationproject.model.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceTypeRepo extends JpaRepository<SpaceType, Integer> {
    // You can add custom queries here if needed
}