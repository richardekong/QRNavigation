package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepo repo;

    @Autowired
    public RoleServiceImpl(RoleRepo repo){
        this.repo = repo;
    }

    @Override
    public Role save(Role role) {
        return repo.save(role);
    }
}
