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
    public List<Space> getAllSpaces(int organizationId) {
        return spaceRepo.findAllSpaces(organizationId);
    }
    @Override
    public Optional<Space> getSpaceById(int id) {
        return Optional.ofNullable(spaceRepo.findById(id));
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
    public Space findByName(String spaceName){ return spaceRepo.findByName(spaceName);}
    @Override
    public Space findById(int id){ return spaceRepo.findById(id);}
}