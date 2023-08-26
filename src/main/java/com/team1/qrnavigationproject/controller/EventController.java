package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.configuration.AuthenticatedUser;
import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VenueService venueService;
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
    @Autowired
    public void setVenueService(VenueService venueService){ this.venueService = venueService; }
    @GetMapping("/admin/events")
    public String ShowEventManagementPage(Model model, Authentication authentication) {
        User admin = AuthenticatedUser.requestCurrentUser(authentication, userService);
        if (admin == null){
            return "redirect:/login";
        }
        admin.getOrganization().getId();
        int organizationId = admin.getOrganization().getId();

        // creating the map
        Map<Event, List<HashMap<String, String>>> eventVenueInfoMap = new HashMap<>();


        // Here retrieving all events by organization id
        List<Event> events = eventService.findAll(organizationId);
        // Here loop through the event list
        for (Event event : events){
            // here getting list of venues of each event
            List<Venue> venues = venueService.findAllByEventVenuesByEventId(event.getId());
            // map fot the info
            List<HashMap<String, String>> venueInfoList = new ArrayList<>();
            for ( Venue venue : venues){
                // here getting the space for each venue
                Space space = spaceService.findById(venue.getSpaceId());
                String venueName = space.getName();
                String venueId = Integer.toString(space.getId());
                //here check if there is subspace for that venue
                if ( venue.getSubspaceId() != 0){
                    // here if there is subspace in the  venue , retrieve it
                    SubSpace subSpace = subSpaceService.findById(venue.getSubspaceId());
                    venueName = venueName +" / "+ subSpace.getName();
                    venueId = Integer.toString(subSpace.getId());
                }

                HashMap<String, String> venueInfo = new HashMap<>();
                venueInfo.put("venueName", venueName);
                venueInfo.put("venueId", venueId);
                venueInfoList.add(venueInfo);
                System.out.println(" ******** "+venueName+ " // "+ venueId);
            }

            eventVenueInfoMap.put(event, venueInfoList);

        }

        model.addAttribute("events", events);
        model.addAttribute("eventVenueInfoMap", eventVenueInfoMap);

        // Print the contents of the eventVenueInfoMap
        for (Map.Entry<Event, List<HashMap<String, String>>> entry : eventVenueInfoMap.entrySet()) {
            Event event = entry.getKey();
            List<HashMap<String, String>> venueInfoList = entry.getValue();

            System.out.println("Event: " + event.getName());
            for (HashMap<String, String> venueInfo : venueInfoList) {
                System.out.println("Venue Name: " + venueInfo.get("venueName") + ", Venue ID: " + venueInfo.get("venueId"));
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

        eventService.saveEvent(event);

        // Get the selected venues and subspaces from the request
        String[] selectedVenues = request.getParameterValues("space");
        String[] selectedSubspaces = request.getParameterValues("subSpace");

        if (selectedVenues != null) {
            for (String venueId : selectedVenues) {
                Space space = spaceService.findByName(venueId);
                Venue venue = new Venue();
                venue.setEvent(event);
                venue.setSpaceId(space.getId());
                venueService.save(venue);
                event.add(venue);
                System.out.println("$$$ venue :" + venue.getId());
            }
        }

        if (selectedSubspaces != null) {
            for (String subspaceId : selectedSubspaces) {
                int subspaceIdInt = Integer.parseInt(subspaceId);
                Venue venue = new Venue();
                venue.setEvent(event);
                venue.setSubspaceId(subspaceIdInt);
                SubSpace subSpace = subSpaceService.findById(subspaceIdInt);
                venue.setSpaceId(subSpace.getSpace().getId());
                venueService.save(venue);
                event.add(venue);
                System.out.println("$$$ venue :" + venue.getId());
            }
        }



        return "redirect:/admin/events";
    }
    @PostMapping("/admin/events/deleteEvent")
    public String deleteEvent(@RequestParam("eventId") int eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/admin/events";
    }
    @PostMapping("/admin/events/viewEvent")
    public String viewEventPage(@RequestParam("eventId") int eventId, Model model, Authentication authentication) {
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
