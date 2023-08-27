package com.team1.qrnavigationproject.service;
import com.team1.qrnavigationproject.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AddressService {
    List<Address> getAllAddresses();
    Optional<Address> getAddressById(int id);
    Address saveAddress(Address address);
    void deleteAddress(int id);

    Address updateAddress(Address addressUp);
    Address findAddressById(int AddressId);
}

