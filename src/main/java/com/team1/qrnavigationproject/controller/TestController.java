package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.SubSpace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/subspaces")
    public List<SubSpace> getTestSubspaces() {
        List<SubSpace> testSubSpaces = new ArrayList<>();

        // Create and add a few test SubSpace objects
        SubSpace subSpace1 = new SubSpace();
        subSpace1.setId(1);
        subSpace1.setName("Test SubSpace 1");
//        subSpace1.setDescription("Test description 1");
//        subSpace1.setPhotoURL("https://example.com/image1.jpg");
//        testSubSpaces.add(subSpace1);

        SubSpace subSpace2 = new SubSpace();
        subSpace2.setId(2);
        subSpace2.setName("Test SubSpace 2");
//        subSpace2.setDescription("Test description 2");
//        subSpace2.setPhotoURL("https://example.com/image2.jpg");
//        testSubSpaces.add(subSpace2);

        return testSubSpaces;
    }
}
