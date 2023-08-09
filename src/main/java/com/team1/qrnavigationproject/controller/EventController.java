package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.response.Response;
import com.team1.qrnavigationproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class EventController {
    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService){
        this.eventService = eventService;
    }


    @GetMapping("/admin/events")
    public String ShowEventManagementPage(Model model) {
        Optional<List<Event>> unConfirmedEvents = Optional.of(eventService.findAll());
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
    public String ViewCreateEventPage() {
        return "createEventPage";
    }

    @PostMapping("/admin/events/createNewEvent")
    public String CreateNewEvent(@ModelAttribute Event event, @RequestParam Map<String, String> requestParams) {

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
