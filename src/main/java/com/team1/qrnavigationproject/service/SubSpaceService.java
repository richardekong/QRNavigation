package com.team1.qrnavigationproject.service;
import com.team1.qrnavigationproject.model.SubSpace;

import java.util.List;
import java.util.Optional;

public interface SubSpaceService {
    List<SubSpace> getAllSubSpaces();
    Optional<SubSpace> getSubSpaceById(int id);
    SubSpace saveSubSpace(SubSpace subSpace);
    void deleteSubSpace(int id);
    Optional<SubSpace> getSubSpaceByName(String subSpaceName);

    List<SubSpace> getSubspacesBySpaceId(Integer spaceId);
}
