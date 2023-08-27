package com.team1.qrnavigationproject.controller;


import com.team1.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;

@Controller
public class userContentController {
    private OrganizationService organizationService;
    private SpaceService spaceService;
    private SubSpaceService subSpaceService;
    private AddressService addressService;
    private EventService eventService;
    private VenueService venueService;
    private ContentService contentService;

    @Autowired
    public void setOrganizationService(OrganizationService organizationService){ this.organizationService = organizationService;}
    @Autowired
    public void setSpaceService(SpaceService spaceService){ this.spaceService = spaceService;}
    @Autowired
    public void setSubSpaceService(SubSpaceService subSpaceService){ this.subSpaceService = subSpaceService;}
    @Autowired
    public void setAddressService(AddressService addressService){ this.addressService = addressService;}
    @Autowired
    public void setEventService(EventService eventService){ this.eventService = eventService;}
    @Autowired
    public void setVenueService(VenueService venueService){ this.venueService = venueService;}
    @Autowired
    public void setContentService(ContentService contentService){ this.contentService = contentService; }

    @GetMapping("/content")
    public String viewContentPage() {
        return "contentPage";
    }

    @GetMapping("/{organization}/{space}/{subspace}")
    public String viewContentPage(
            @PathVariable String organization,
            @PathVariable String space,
            @PathVariable String subspace,
            Model model) {

        Organization org = organizationService.findByName(organization);
        Space sp = spaceService.findByName(space);
        SubSpace sub = subSpaceService.findByName(subspace);

        Address address = addressService.findAddressById(sp.getAddressId());


        // creating the map
        Map<Event, List<HashMap<String, String>>> eventVenueInfoMap = new HashMap<>();

        List<Event> events = eventService.findEventsWithin2Days(sub.getId());
        // Here loop through the event list
        for (Event event : events){
            // here getting list of venues of each event
            List<Venue> venues = venueService.findAllByEventVenuesByEventId(event.getId());
            // map fot the info
            List<HashMap<String, String>> venueInfoList = new ArrayList<>();
            for ( Venue venue : venues){
                // here getting the space for each venue
                Space venueSpace = spaceService.findById(venue.getSpaceId());
                String venueName = venueSpace.getName();
                String venueId = Integer.toString(venueSpace.getId());
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

        List<Content> contents = contentService.getContentByEventAndSpaceAndSubSpace(events,sp.getId(),sub.getId());

        model.addAttribute("sp", sp);
        model.addAttribute("sub", sub);
        model.addAttribute("address", address);
        model.addAttribute("events", events);
        model.addAttribute("eventVenueInfoMap", eventVenueInfoMap);
        model.addAttribute("contents", contents);

        return "contentPage";
    }
}
