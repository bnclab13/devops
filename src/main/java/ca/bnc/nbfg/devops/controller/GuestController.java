package ca.bnc.nbfg.devops.controller;

import ca.bnc.nbfg.devops.model.EventGuest;
import ca.bnc.nbfg.devops.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping("/guests/{email}/events")
    public Set<EventGuest> getEventsByGuest(@PathVariable String email) {
        return guestService.getEventListByEmail(email);
    }

    @PutMapping("/guests/{guestId}/events/{eventId}/accept")
    public void acceptGuestInvitation(@PathVariable long guestId,@PathVariable long eventId) {
        guestService.setInvitationStatus(guestId, eventId,EventGuest.InvitationStatus.ACCEPTED);
    }

    @PutMapping("/guests/{guestId}/events/{eventId}/decline")
    public void declineGuestInvitation(@PathVariable long guestId,@PathVariable long eventId) {
        guestService.setInvitationStatus(guestId, eventId,EventGuest.InvitationStatus.DECLINED);
    }
}
