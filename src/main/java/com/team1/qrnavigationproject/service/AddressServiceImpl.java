package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Address;
import com.team1.qrnavigationproject.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepo.findAll();
    }

    @Override
    public Optional<Address> getAddressById(int id) {
        return addressRepo.findById(id);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public void deleteAddress(int id) {
        addressRepo.deleteById(id);
    }
    @Override
    public Address updateAddress(Address addressUp) {
        return null;
    }
    @Override
    public Address findAddressById(int AddressId){ return addressRepo.findAddressById(AddressId); }


}

