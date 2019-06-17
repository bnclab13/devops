package ca.bnc.nbfg.devops.controller;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/events/{id}/guests")
    public ResponseEntity<String> inviteGuestToEvent(@PathVariable Long id, @RequestBody List<Guest> guests){
        eventService.inviteGuests(id,guests);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        ResponseEntity<Void> response;

        if (eventService.updateEvent(id, event)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        return response;
    }
}
