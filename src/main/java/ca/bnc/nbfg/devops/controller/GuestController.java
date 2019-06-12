package ca.bnc.nbfg.devops.controller;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class GuestController {

    @Autowired
    EventService eventService;

    @GetMapping("/guests/{email}/events")
    public List<Event> getEventsByGuest(@PathVariable String email) {
    return eventService.getEventsByGuest(email);
    }
}
