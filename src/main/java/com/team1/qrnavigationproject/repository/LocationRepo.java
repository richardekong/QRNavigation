package com.team1.qrnavigationproject.repository;
import com.team1.qrnavigationproject.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {

}