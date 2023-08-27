package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
    void deleteById(int id);

    Address findAddressById(int id);
}
