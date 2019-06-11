package ca.bnc.nbfg.devops.controller;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/events")
    public List<Event> listEvents() {
        return eventService.getSortedEvents();
    }

    @PutMapping("/events/{id}/cancel")
    public void cancelEvent(@PathVariable Long id){
        eventService.cancelEvent(id);
    }
}
