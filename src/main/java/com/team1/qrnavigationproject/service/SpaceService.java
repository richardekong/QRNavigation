package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Space;
import java.util.List;
import java.util.Optional;

public interface SpaceService {
    public List<Space> getAllSpaces(int organizationId);
    public Optional<Space> getSpaceById(int id);
    public Space saveSpace(Space space);
    public void deleteSpace(int id);
    public Optional<Space> getSpaceByName(String name);
    Space findById(int id);
}
