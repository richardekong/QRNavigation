package com.team1.qrnavigationproject.service;


import com.team1.qrnavigationproject.model.SpaceType;

import java.util.List;
import java.util.Optional;

public interface SpaceTypeService {
    public List<SpaceType> getAllSpaceTypes();

    public Optional<SpaceType> getSpaceTypeById(int id);

    public SpaceType saveSpaceType(SpaceType spaceType);

    public void deleteSpaceType(int id);
}
