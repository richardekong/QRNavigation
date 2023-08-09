package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/qrnavigation/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    @Autowired
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @GetMapping
    public ResponseEntity<List<Space>> getAllSpaces() {
        List<Space> spaces = spaceService.getAllSpaces();
        return new ResponseEntity<>(spaces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Space> getSpaceById(@PathVariable int id) {
        return spaceService.getSpaceById(id)
                .map(space -> new ResponseEntity<>(space, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Space> createSpace(@Valid @RequestBody Space space) {
        Space savedSpace = spaceService.saveSpace(space);
        return new ResponseEntity<>(savedSpace, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Space> updateSpace(@PathVariable int id, @Valid @RequestBody Space space) {
        return spaceService.getSpaceById(id)
                .map(existingSpace -> {
                    space.setId(id);
                    Space updatedSpace = spaceService.saveSpace(space);
                    return new ResponseEntity<>(updatedSpace, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable int id) {
        spaceService.deleteSpace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{spaceName}")
    public ResponseEntity<Space> getSpaceByName(@PathVariable String spaceName) {
        return spaceService.getSpaceByName(spaceName)
                .map(space -> new ResponseEntity<>(space, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}