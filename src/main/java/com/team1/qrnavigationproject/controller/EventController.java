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
        /////////
        Optional<List<Event>> unConfirmedEvents = Optional.of(eventService.findAll(organizationId));
        unConfirmedEvents.ifPresentOrElse(
                events -> model.addAttribute("events", events),
                () -> {
                    HttpStatus status = HttpStatus.NO_CONTENT;
                    model.addAttribute("eventsLoadErrorDisplay", new Response(status.value(), status.getReasonPhrase(), System.currentTimeMillis()));
                    throw new CustomException(HttpStatus.NO_CONTENT.getReasonPhrase(), HttpStatus.NO_CONTENT);
                });

        model.addAttribute(
                "eventsLoadSuccessDisplay",
                new Response(HttpStatus.OK.value(), "Events Loaded Successfully!", System.currentTimeMillis())
        );
        model.addAttribute("events", unConfirmedEvents.get());
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
        admin.getOrganization().getId();
        int organizationId = admin.getOrganization().getId();
        //
//        event.setOrganizer(admin.getOrganization());


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
        String[] selectedVenues = request.getParameterValues("venue");
        String[] selectedSubspaces = request.getParameterValues("subspace");

        // Print selected venues
        if (selectedVenues != null) {
            System.out.println("*** Selected Venues:");
            for (String venueId : selectedVenues) {
                System.out.println(" *** Venue ID: " + venueId);
            }
        }

        // Print selected subspaces
        if (selectedSubspaces != null) {
            System.out.println("Selected Subspaces:");
            for (String subspaceId : selectedSubspaces) {
                System.out.println("Subspace ID: " + subspaceId);
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
    @PostMapping("/admin/events/viewEvent")
    public String viewEventPage(@RequestParam("eventId") int eventId,  Model model) {
        Event event = eventService.findEventById(eventId);
        model.addAttribute("event", event);
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
