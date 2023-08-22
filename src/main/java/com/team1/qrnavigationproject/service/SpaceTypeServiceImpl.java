package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.SpaceType;
import com.team1.qrnavigationproject.repository.SpaceTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceTypeServiceImpl implements SpaceTypeService {

    private final SpaceTypeRepo spaceTypeRepo;

    @Autowired
    public SpaceTypeServiceImpl(SpaceTypeRepo spaceTypeRepo) {
        this.spaceTypeRepo = spaceTypeRepo;
    }

    public List<SpaceType> getAllSpaceTypes() {
        return spaceTypeRepo.findAll();
    }

    public Optional<SpaceType> getSpaceTypeById(int id) {
        return spaceTypeRepo.findById(id);
    }

    public SpaceType saveSpaceType(SpaceType spaceType) {
        return spaceTypeRepo.save(spaceType);
    }

    public void deleteSpaceType(int id) {
        spaceTypeRepo.deleteById(id);
    }


}
