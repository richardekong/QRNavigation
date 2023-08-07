package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.SpaceType;
import com.team1.qrnavigationproject.service.SpaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/qrnavigation/space-types")
public class SpaceTypeController {

    private final SpaceTypeService spaceTypeService;

    @Autowired
    public SpaceTypeController(SpaceTypeService spaceTypeService) {
        this.spaceTypeService = spaceTypeService;
    }

    @GetMapping
    public ResponseEntity<List<SpaceType>> getAllSpaceTypes() {
        List<SpaceType> spaceTypes = spaceTypeService.getAllSpaceTypes();
        return new ResponseEntity<>(spaceTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceType> getSpaceTypeById(@PathVariable int id) {
        return spaceTypeService.getSpaceTypeById(id)
                .map(spaceType -> new ResponseEntity<>(spaceType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SpaceType> createSpaceType(@Valid @RequestBody SpaceType spaceType) {
        SpaceType savedSpaceType = spaceTypeService.saveSpaceType(spaceType);
        return new ResponseEntity<>(savedSpaceType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceType> updateSpaceType(@PathVariable int id, @Valid @RequestBody SpaceType spaceType) {
        return spaceTypeService.getSpaceTypeById(id)
                .map(existingSpaceType -> {
                    spaceType.setId(id);
                    SpaceType updatedSpaceType = spaceTypeService.saveSpaceType(spaceType);
                    return new ResponseEntity<>(updatedSpaceType, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceType(@PathVariable int id) {
        spaceTypeService.deleteSpaceType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}