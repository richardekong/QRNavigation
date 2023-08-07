package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.service.SubSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/qrnavigation/sub-spaces")
public class SubSpaceController {

    private final SubSpaceService subSpaceService;

    @Autowired
    public SubSpaceController(SubSpaceService subSpaceService) {
        this.subSpaceService = subSpaceService;
    }

    @GetMapping
    public ResponseEntity<List<SubSpace>> getAllSubSpaces() {
        List<SubSpace> subSpaces = subSpaceService.getAllSubSpaces();
        return new ResponseEntity<>(subSpaces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubSpace> getSubSpaceById(@PathVariable int id) {
        return subSpaceService.getSubSpaceById(id)
                .map(subSpace -> new ResponseEntity<>(subSpace, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SubSpace> createSubSpace(@Valid @RequestBody SubSpace subSpace) {
        SubSpace savedSubSpace = subSpaceService.saveSubSpace(subSpace);
        return new ResponseEntity<>(savedSubSpace, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubSpace> updateSubSpace(@PathVariable int id, @Valid @RequestBody SubSpace subSpace) {
        return subSpaceService.getSubSpaceById(id)
                .map(existingSubSpace -> {
                    subSpace.setId(id);
                    SubSpace updatedSubSpace = subSpaceService.saveSubSpace(subSpace);
                    return new ResponseEntity<>(updatedSubSpace, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubSpace(@PathVariable int id) {
        subSpaceService.deleteSubSpace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{subSpaceName}")
    public ResponseEntity<SubSpace> getSubSpaceByName(@PathVariable String subSpaceName) {
        return subSpaceService.getSubSpaceByName(subSpaceName)
                .map(subSpace -> new ResponseEntity<>(subSpace, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}