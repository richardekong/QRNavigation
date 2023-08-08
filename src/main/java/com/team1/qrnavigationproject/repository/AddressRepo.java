package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Integer> {
    void deleteById(int id);
}
