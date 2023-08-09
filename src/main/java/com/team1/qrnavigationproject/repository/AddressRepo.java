package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Integer> {
}
