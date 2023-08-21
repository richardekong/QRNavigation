package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.repository.SpaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepo spaceRepo;

    @Autowired
    public SpaceServiceImpl(SpaceRepo spaceRepo) {
        this.spaceRepo = spaceRepo;
    }

    @Override
    public List<Space> getAllSpaces() {
        return spaceRepo.findAll();
    }

    @Override
    public Optional<Space> getSpaceById(int id) {
        return spaceRepo.findById(id);
    }

    @Override
    public Space saveSpace(Space space) {
        return spaceRepo.save(space);
    }

    @Override
    public void deleteSpace(int id) {
        spaceRepo.deleteById(id);
    }

    @Override
    public Optional<Space> getSpaceByName(String name) {
        return spaceRepo.findSpaceByName(name);
    }

    @Override
    public Space updateSpace(Space spaceUp) {
        Space byId = spaceRepo.findById(spaceUp.getId()).get();
        byId.setName(spaceUp.getName());
        byId.setDescription(spaceUp.getDescription());
        return spaceRepo.save(byId);
    }

}