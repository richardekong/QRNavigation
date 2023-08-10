package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.UserType;
import com.team1.qrnavigationproject.repository.UserTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeServiceImpl implements UserTypeService {
    private UserTypeRepo repo;

    @Autowired
    public  UserTypeServiceImpl(UserTypeRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserType save(UserType userType) {
        return repo.save(userType);
    }

}
