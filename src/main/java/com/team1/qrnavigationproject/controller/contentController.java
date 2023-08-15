package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.Content;
import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.response.Response;
import com.team1.qrnavigationproject.service.ContentService;
import com.team1.qrnavigationproject.service.EventService;
import com.team1.qrnavigationproject.service.SpaceService;
import com.team1.qrnavigationproject.service.SubSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class contentController {

    private ContentService contentService;
    private EventService eventService;
    private SpaceService spaceService;

    private SubSpaceService subSpaceService;

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

    @GetMapping("/admin/contents")
    public String ShowContentManagementPage(Model model) {
        // This list stores the unique IDs of events that have appeared in the content table.
        List<Integer> listOfEventsIdsInContentTable = contentService.findDistinctContentIds();
        List<Object[]> eventNamesAndIdsList = eventService.findEventNamesAndIdsByIds(listOfEventsIdsInContentTable);
        // passing the list eventNamesAndIdsList to view ( contentManagementPage )
        model.addAttribute("eventNamesAndIdsList", eventNamesAndIdsList);

//        Optional<List<Content>> unConfirmedContents = Optional.of(contentService.findAll());
//        unConfirmedContents.ifPresentOrElse(
//                contents -> model.addAttribute("contents", contents),
//                () -> {
//                    HttpStatus status = HttpStatus.NO_CONTENT;
//                    model.addAttribute("ContentsLoadErrorDisplay", new Response(status.value(), status.getReasonPhrase(), System.currentTimeMillis()));
//                    throw new CustomException(HttpStatus.NO_CONTENT.getReasonPhrase(), HttpStatus.NO_CONTENT);
//                });
//
//        model.addAttribute(
//                "contentsLoadSuccessDisplay",
//                new Response(HttpStatus.OK.value(), "Contents Loaded Successfully!", System.currentTimeMillis())
//        );
//        model.addAttribute("contents", unConfirmedContents.get());
        List<Object[]> contentWithNames = contentService.findAll();
        List<Object[]> contentInfoList = new ArrayList<>();

        for (Object[] result : contentWithNames) {
            Content content = (Content) result[0];
            String eventName = (String) result[1];
            String subSpaceName = (String) result[2];
            String spaceName = (String) result[3];

            Object[] contentInfo = { content, eventName, subSpaceName, spaceName };
            contentInfoList.add(contentInfo);
        }

        model.addAttribute("contentInfoList", contentInfoList);
        return "contentManagementPage";
    }

    @GetMapping("/admin/contents/createContent")
    public String ViewCreateContentPage(Model model) {
        List<Event> events = eventService.findAll();
        // passing the list events to view ( createContentPage )
        model.addAttribute("events", events);

        List<Space> spaces = spaceService.getAllSpaces();
        // passing the list spaces to view ( createContentPage )
        model.addAttribute("spaces", spaces);

        //
        //List<SubSpace> subSpaces = subSpaceService.getSubspacesBySpaceId(spaceId);

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
    public String viewContentPage(@RequestParam Map<String, String> requestParams, Model model) {
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
        /////


        List<Event> events = eventService.findAll();
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
        int eventId = Integer.parseInt(paramMap.get("event.id"));
        int spaceId = Integer.parseInt(paramMap.get("space.id"));
        int subSpaceId = Integer.parseInt(paramMap.get("subspace.id"));


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
