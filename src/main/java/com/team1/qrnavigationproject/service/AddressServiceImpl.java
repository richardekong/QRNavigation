package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{
    private AddressRepo addressRepo;

    @Autowired
    private void setAddressRepo(AddressRepo addressRepo){
        this.addressRepo = addressRepo;
    }
}
