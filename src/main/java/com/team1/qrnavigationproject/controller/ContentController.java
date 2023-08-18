package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.configuration.AuthenticatedUser;
import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ContentController {
    private ContentService contentService;
    private EventService eventService;
    private SpaceService spaceService;
    private SubSpaceService subSpaceService;
    private UserService userService;

    @Autowired
    public void setContentService(ContentService contentService){
        this.contentService = contentService;
    }
    @Autowired
    public void setEventService(EventService eventService) { this.eventService = eventService;}
    @Autowired
    public void SetSpaceService(SpaceService spaceService){ this.spaceService = spaceService;}
    @Autowired
    public void setSubSpaceService(SubSpaceService subSpaceService){ this.subSpaceService = subSpaceService;}
    @Autowired
    public void setUserService(UserService userService){ this.userService = userService;}

    @GetMapping("/admin/contents")
    public String ShowContentManagementPage(Model model, Authentication authentication) {
        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null){
            return "redirect:/login";
        }

        // HERE I'm getting the or organization id
        int organizationId = admin.getOrganization().getId();

        // This list stores the unique IDs of events that have appeared in the content table and created by organization its id passed.
        List<Integer> listOfEventsIdsInContentTable = contentService.findDistinctContentIds(organizationId);
        // this list of objects passed to view as  which will be implemented later
        List<Object[]> eventNamesAndIdsList = eventService.findEventNamesAndIdsByIds(listOfEventsIdsInContentTable);
        // passing the list eventNamesAndIdsList to view ( contentManagementPage )
        model.addAttribute("eventNamesAndIdsList", eventNamesAndIdsList);

        // here getting all contents created by organization by passing organization id
        List<Object[]> contentWithNames = contentService.findAll(organizationId);
        List<Object[]> contentInfoList = new ArrayList<>();
        for (Object[] result : contentWithNames) {
            Content content = (Content) result[0];
            String eventName = (String) result[1];
            String subSpaceName = (String) result[2];
            String spaceName = (String) result[3];
            Object[] contentInfo = { content, eventName, subSpaceName, spaceName };
            contentInfoList.add(contentInfo);
        }

        // here passing retrieved contents to view
        model.addAttribute("contentInfoList", contentInfoList);
        return "contentManagementPage";
    }

    @GetMapping(value = "/admin/contents/getContents/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Map<String, Object>> getContentsByEventId(@PathVariable("eventId") int eventId) {
        // here getting the contents with its information for specific event
        List<Object[]> contentWithNamesByEventId = contentService.getContentsByEventId(eventId);
        List<Object[]> contentInfoList = new ArrayList<>();
        for (Object[] result : contentWithNamesByEventId) {
            Content content = (Content) result[0];
            String eventName = (String) result[1];
            String subSpaceName = (String) result[2];
            String spaceName = (String) result[3];
            Object[] contentInfo = { content, eventName, subSpaceName, spaceName };
            contentInfoList.add(contentInfo);
        }

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] result : contentInfoList) {
            Content content = (Content) result[0];
            String eventName = (String) result[1];
            String subSpaceName = (String) result[2];
            String spaceName = (String) result[3];

            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("contentId", content.getId());
            contentMap.put("contentName", content.getName());
            contentMap.put("eventName", eventName);
            contentMap.put("subSpaceName", subSpaceName);
            contentMap.put("spaceName", spaceName);
            response.add(contentMap);

        }
        return response;
    }

    @GetMapping("/admin/contents/createContent")
    public String ViewCreateContentPage(Model model,Authentication authentication) {
        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null){
            return "redirect:/login";
        }
        admin.getOrganization().getId();
        int organizationId = admin.getOrganization().getId();
        // Getting list of events created by organization by passing organization id to the service
        List<Event> events = eventService.findAll(organizationId);
        // passing the list events objects to view ( createContentPage )
        model.addAttribute("events", events);
        // getting list of spaces
        List<Space> spaces = spaceService.getAllSpaces();
        // passing the list spaces to view ( createContentPage )
        model.addAttribute("spaces", spaces);


        return "createContentPage";
    }

    @GetMapping(value = "/admin/contents/getSubspaces/{spaceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Map<String, Object>> getSubspacesBySpaceId(@PathVariable("spaceId") int spaceId) {
        List<SubSpace> subSpaces = subSpaceService.getSubspacesBySpaceId(spaceId);

        List<Map<String, Object>> response = new ArrayList<>();
        for (SubSpace subSpace : subSpaces) {
            Map<String, Object> subSpaceMap = new HashMap<>();
            subSpaceMap.put("id", subSpace.getId());
            subSpaceMap.put("name", subSpace.getName());
            response.add(subSpaceMap);
        }

        return response;
    }

    @PostMapping("/admin/contents/createNewContent")
    public String createNewContent(@ModelAttribute Content content,
                                   @RequestParam Map<String, String> paramMap) {
        int eventId = Integer.parseInt(paramMap.get("event.id"));
        int spaceId = Integer.parseInt(paramMap.get("space.id"));
        int subSpaceId = Integer.parseInt(paramMap.get("subspace.id"));

        Event event = eventService.findEventById(eventId);
        Space space = spaceService.findById(spaceId);
        SubSpace subSpace = subSpaceService.findById(subSpaceId);

        content.setEvent(event);
        content.setSpace(space);
        content.setSubSpace(subSpace);


        contentService.saveContent(content);

        return "redirect:/admin/contents";
    }


    @PostMapping("/admin/contents/deleteContent")
    public String deleteContent(@RequestParam("contentId") int contentId) {
        contentService.deleteContentById(contentId);
        return "redirect:/admin/contents";
    }

    @PostMapping("/admin/contents/viewContent")
    public String viewContentPage(@RequestParam Map<String, String> requestParams, Model model , Authentication authentication) {
        System.out.println("************ HERE *************");
        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null){
            return "redirect:/login";
        }
        admin.getOrganization().getId();
        int organizationId = admin.getOrganization().getId();

        // getting the content by its id
        int contentId = Integer.parseInt(requestParams.get("contentId"));
        String contentName = requestParams.get("contentName");
        String eventName = requestParams.get("eventName");
        String spaceName = requestParams.get("spaceName");
        String subspaceName = requestParams.get("subspaceName");

        Content content = contentService.findContentById(contentId);
        model.addAttribute("content", content);
        model.addAttribute("contentName", contentName);
        model.addAttribute("eventName", eventName);
        model.addAttribute("spaceName", spaceName);
        model.addAttribute("subspaceName", subspaceName);



        List<Event> events = eventService.findAll(organizationId);
        // passing the list events to view ( createContentPage )
        model.addAttribute("events", events);

        List<Space> spaces = spaceService.getAllSpaces();
        // passing the list spaces to view ( createContentPage )
        model.addAttribute("spaces", spaces);

        List<SubSpace> spaceSubSpaces = subSpaceService.getSubspacesBySpaceId(content.getSpace().getId());
        model.addAttribute("spaceSubSpaces", spaceSubSpaces);


        return "contentUpdate";
    }

    @PostMapping("/admin/contents/updateContent")
    public String UpdateContent(@ModelAttribute Content content,
                                @RequestParam Map<String, String> paramMap) {
        // getting the ids from the form
        int eventId = Integer.parseInt(paramMap.get("event.id"));
        int spaceId = Integer.parseInt(paramMap.get("space.id"));
        int subSpaceId = Integer.parseInt(paramMap.get("subspace.id"));

        // retrieving the objects by their ids
        Event event = eventService.findEventById(eventId);
        Space space = spaceService.findById(spaceId);
        SubSpace subSpace = subSpaceService.findById(subSpaceId);


        content.setEvent(event);
        content.setSpace(space);
        content.setSubSpace(subSpace);
        contentService.updateContent(content);


        return "redirect:/admin/contents";
    }
}
