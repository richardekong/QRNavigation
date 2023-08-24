package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.configuration.AuthenticatedUser;
import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.response.Response;
import com.team1.qrnavigationproject.service.EventService;
import com.team1.qrnavigationproject.service.SpaceService;
import com.team1.qrnavigationproject.service.SubSpaceService;
import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;
    private SpaceService spaceService;
    private SubSpaceService subSpaceService;
    @Autowired
    public void setEventService(EventService eventService){
        this.eventService = eventService;
    }
    @Autowired
    public void setUserService(UserService userService){ this.userService = userService;}
    @Autowired
    public void setSpaceService(SpaceService spaceService){ this.spaceService = spaceService;}
    @Autowired
    public void setSubSpaceService(SubSpaceService subSpaceService){ this.subSpaceService = subSpaceService;}
    @GetMapping("/admin/events")
    public String ShowEventManagementPage(Model model, Authentication authentication) {
        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null){
            return "redirect:/login";
        }
        admin.getOrganization().getId();
        int organizationId = admin.getOrganization().getId();

        List<Event> events = eventService.findAll(organizationId);
        Map<Event, Map<String, List<String>>> combinedMap = new HashMap<>();

        for (Event e : events) {
            Map<String, List<String>> spaceSubSpaceMap = new HashMap<>();
            List<Space> spaces = spaceService.findAllSpacesByEvent(e.getId());
            for (Space s : spaces) {
                List<SubSpace> subSpaces = subSpaceService.getSubSpaceByEvent(e.getId(), s.getId());
                List<String> subSpaceNames = new ArrayList<>();
                for (SubSpace subSpace : subSpaces) {
                    String subSpaceName = subSpace.getName() + " / " + s.getName();
                    subSpaceNames.add(subSpaceName);
                }
                spaceSubSpaceMap.put(s.getName(), subSpaceNames);
            }
            combinedMap.put(e, spaceSubSpaceMap);
        }

        model.addAttribute("combinedMap", combinedMap);
        System.out.println("============================");
        // Printing the content of combinedMap
        // Loop through the combinedMap and print its content
        for (Map.Entry<Event, Map<String, List<String>>> entry : combinedMap.entrySet()) {
            Event event = entry.getKey();
            System.out.println("Event: " + event.getName() + " (ID: " + event.getId() + ")");

            Map<String, List<String>> spaceSubSpaceMap = entry.getValue();

            for (Map.Entry<String, List<String>> spaceEntry : spaceSubSpaceMap.entrySet()) {
                String spaceName = spaceEntry.getKey();
                List<String> subSpaceNames = spaceEntry.getValue();

                System.out.println("  Space: " + spaceName);
                if (subSpaceNames.isEmpty()) {
                    System.out.println("    No subspaces");
                } else {
                    System.out.println("    Subspaces: " + subSpaceNames);
                }
            }
        }





        return "eventManagementPage";
    }


    @GetMapping("/admin/events/createEvent")
    public String ViewCreateEventPage(Model model, Authentication authentication) {
        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null){
            return "redirect:/login";
        }
        admin.getOrganization().getId();
        int organizationId = admin.getOrganization().getId();


        List<Space> spaceList = spaceService.getAllSpaces(organizationId);
        Map<String, List<Map<String, String>>> spaceSubspaceMap = new LinkedHashMap<>();

        for (Space space : spaceList) {
            List<SubSpace> subSpaceList = subSpaceService.getSubspacesBySpaceId(space.getId());
            List<Map<String, String>> subspaceInfoList = new ArrayList<>();  // Change the type to List<Map<String, String>>

            for (SubSpace subSpace : subSpaceList) {
                Map<String, String> subspaceInfo = new HashMap<>();  // Change the type to Map<String, String>
                subspaceInfo.put("type", "subSpace");  // Change spaceInfo.put to subspaceInfo.put
                subspaceInfo.put("id", String.valueOf(subSpace.getId()));  // Convert ID to String
                subspaceInfo.put("name", " " + subSpace.getName() + " / " + space.getName());
                subspaceInfoList.add(subspaceInfo);
            }

            spaceSubspaceMap.put(space.getName(), subspaceInfoList);
        }

        model.addAttribute("spaceSubspaceMap", spaceSubspaceMap);




        return "createEventPage";
    }
    @PostMapping("/admin/events/createNewEvent")
    public String CreateNewEvent(@ModelAttribute Event event, @RequestParam Map<String, String> requestParams, HttpServletRequest request, Authentication authentication) {
        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null){
            return "redirect:/login";
        }

       event.setOrganizer(admin.getOrganization());


        LocalDate event_start_date = LocalDate.parse(requestParams.get("event_start_date"));
        LocalTime event_start_time = LocalTime.parse(requestParams.get("event_start_time"));
        LocalDate event_end_date = LocalDate.parse(requestParams.get("event_end_date"));
        LocalTime event_end_time = LocalTime.parse(requestParams.get("event_end_time"));

        // Combine date and time to create LocalDateTime
        LocalDateTime start = event_start_date.atTime(event_start_time);
        event.setStart(start);

        LocalDateTime end = event_end_date.atTime(event_end_time);
        event.setEnd(end);

        // Get the selected venues and subspaces from the request
        String[] selectedVenues = request.getParameterValues("space");
        String[] selectedSubspaces = request.getParameterValues("subSpace");

        // Assign the event  to the selected spaces
        if (selectedVenues != null) {
            for (String venueId : selectedVenues) {
                Space space = spaceService.findByName(venueId);
                space.setEvent(event);
            }
        }

        // Assign the event to the selectedSubspaces
        if (selectedSubspaces != null) {
            for (String subspaceId : selectedSubspaces) {

                int subspaceIdInt = Integer.parseInt(subspaceId);
                SubSpace subSpace = subSpaceService.findById(subspaceIdInt);
                subSpace.setEvent(event);
            }
        }


        eventService.saveEvent(event);

        return "redirect:/admin/events";
    }
    @PostMapping("/admin/events/deleteEvent")
    public String deleteEvent(@RequestParam("eventId") int eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/admin/events";
    }
//    @PostMapping("/admin/events/viewEvent")
//    public String viewEventPage(@RequestParam("eventId") int eventId,  Model model, Authentication authentication) {
//        Event event = eventService.findEventById(eventId);
//        model.addAttribute("event", event);
//        // here
//
//        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
//        if (admin == null){
//            return "redirect:/login";
//        }
//        admin.getOrganization().getId();
//        int organizationId = admin.getOrganization().getId();
//
//
//        List<Space> spaceList = spaceService.getAllSpaces(organizationId);
//        Map<String, List<Map<String, String>>> spaceSubspaceMap = new LinkedHashMap<>();
//
//        for (Space space : spaceList) {
//            boolean isSpaceIncludedInEvent = spaceService.isSpaceIncludedInEvent(event.getId(), space.getId());
//            List<SubSpace> subSpaceList = subSpaceService.getSubspacesBySpaceId(space.getId());
//            List<Map<String, String>> subspaceInfoList = new ArrayList<>();  // Change the type to List<Map<String, String>>
//
//            for (SubSpace subSpace : subSpaceList) {
//                boolean isSubSpaceIncludedInEvent = subSpaceService.isSubSpaceIncludedInEvent(event.getId(), subSpace.getId());
//                Map<String, String> subspaceInfo = new HashMap<>();  // Change the type to Map<String, String>
//                subspaceInfo.put("type", "subSpace");  // Change spaceInfo.put to subspaceInfo.put
//                subspaceInfo.put("id", String.valueOf(subSpace.getId()));  // Convert ID to String
//                subspaceInfo.put("name", " " + subSpace.getName() + " / " + space.getName());
//                subspaceInfoList.add(subspaceInfo);
//            }
//
//            spaceSubspaceMap.put(space.getName(), subspaceInfoList);
//        }
//
//        model.addAttribute("spaceSubspaceMap", spaceSubspaceMap);
//
//
//        return "eventUpdate";
//    }

    @PostMapping("/admin/events/viewEvent")
    public String viewEventPage(@RequestParam("eventId") int eventId, Model model, Authentication authentication) {
        Event event = eventService.findEventById(eventId);
        model.addAttribute("event", event);
        int counter = 1;

        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null) {
            return "redirect:/login";
        }
        int organizationId = admin.getOrganization().getId();

        List<Space> spaceList = spaceService.getAllSpaces(organizationId);
        Map<String, List<Map<String, String>>> spaceSubspaceMap = new LinkedHashMap<>();

        for (Space space : spaceList) {
            List<SubSpace> subSpaceList = subSpaceService.getSubspacesBySpaceId(space.getId());
            List<Map<String, String>> subspaceInfoList = new ArrayList<>();

            for (SubSpace subSpace : subSpaceList) {
                boolean isSpaceIncludedInEvent = spaceService.isSpaceIncludedInEvent(event.getId(), space.getId());
                boolean isSubSpaceIncluded = subSpaceService.isSubSpaceIncludedInEvent(event.getId(), subSpace.getId());

                Map<String, String> subspaceInfo = new HashMap<>();
                subspaceInfo.put("type", "subSpace");
                subspaceInfo.put("id", String.valueOf(subSpace.getId()));
                subspaceInfo.put("name", " " + subSpace.getName() + " / " + space.getName());
                subspaceInfo.put("isSpaceIncludedInEvent", String.valueOf(isSpaceIncludedInEvent));
                subspaceInfo.put("isSubSpaceIncludedInEvent", String.valueOf(isSubSpaceIncluded));
                if (isSpaceIncludedInEvent) {
                    System.out.println("((((((((( " +counter+"   *****£££££££ the space: " + space.getName());
                    counter++;
                }
                if (isSubSpaceIncluded) {
                    System.out.println("((((((((( " +counter+"    *******£££££££ the space: " + space.getName() + " AND THE SUBSPACE :" + subSpace.getName());
                    counter++;
                }
                    subspaceInfoList.add(subspaceInfo);
            }

            spaceSubspaceMap.put(space.getName(), subspaceInfoList);
        }

        model.addAttribute("spaceSubspaceMap", spaceSubspaceMap);

        return "eventUpdate";
    }

    @PostMapping("/admin/events/updateEvent")
    public String UpdateEvent(@ModelAttribute Event event, @RequestParam Map<String, String> requestParams) {

        LocalDate event_start_date = LocalDate.parse(requestParams.get("event_start_date"));
        LocalTime event_start_time = LocalTime.parse(requestParams.get("event_start_time"));
        LocalDate event_end_date = LocalDate.parse(requestParams.get("event_end_date"));
        LocalTime event_end_time = LocalTime.parse(requestParams.get("event_end_time"));

        // Combine date and time to create LocalDateTime
        LocalDateTime start = event_start_date.atTime(event_start_time);
        event.setStart(start);

        LocalDateTime end = event_end_date.atTime(event_end_time);
        event.setEnd(end);

        eventService.updateEvent(event);
        return "redirect:/admin/events";
    }
}
