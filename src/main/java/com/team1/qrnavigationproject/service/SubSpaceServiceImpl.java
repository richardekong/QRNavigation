package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.repository.SubSpaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubSpaceServiceImpl implements SubSpaceService {
    private final SubSpaceRepo subSpaceRepo;
    @Autowired
    public SubSpaceServiceImpl(SubSpaceRepo subSpaceRepo) {
        this.subSpaceRepo = subSpaceRepo;
    }
    @Override
    public List<SubSpace> getAllSubSpaces() {
        return subSpaceRepo.findAll();
    }
    @Override
    public Optional<SubSpace> getSubSpaceById(int id) {
        return Optional.ofNullable(subSpaceRepo.findById(id));
    }
    @Override
    public SubSpace saveSubSpace(SubSpace subSpace) {
        return subSpaceRepo.save(subSpace);
    }
    @Override
    public void deleteSubSpace(int id) {
        subSpaceRepo.deleteById(id);
    }
    @Override
    public Optional<SubSpace> getSubSpaceByName(String subSpaceName) {
        return subSpaceRepo.findSubSpaceByName(subSpaceName);
    }
    @Override
    public List<SubSpace> getSubspacesBySpaceId(Integer spaceId){
        return subSpaceRepo.getSubspacesBySpaceId(spaceId);
    }
    @Override
    public SubSpace findById(int id){ return subSpaceRepo.findById(id);}
//    @Override
//    public List<Object[]> getSpaceAndSubspaceInfo(int orgId){
//        return subSpaceRepo.getSpaceAndSubspaceInfo(orgId);
//    }
    @Override
    public SubSpace findByName(String SubSpaceName){ return subSpaceRepo.findByName(SubSpaceName); }

}