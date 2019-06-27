package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.EventGuest;
import ca.bnc.nbfg.devops.model.EventGuestId;
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.repository.EventGuestRepository;
import ca.bnc.nbfg.devops.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository ;

    @Autowired
    private EventGuestRepository eventGuestRepository;

    public Set<EventGuest> getEventListByEmail(String email){
        List<Guest> guests = guestRepository.findByEmail(email);
        if(guests.size() == 1){
            Guest guest = guests.get(0);
            return guest.getEventGuests();
        }
        else return null;
    }


    public void setInvitationStatus(long guestId, long eventId, EventGuest.InvitationStatus invitationStatus){
        Optional<EventGuest> eventGuestOptional = eventGuestRepository.findById(new EventGuestId(eventId,guestId));
        if (eventGuestOptional.isPresent()){
            EventGuest eventGuest = eventGuestOptional.get();
            eventGuest.setInvitationStatus(invitationStatus);
            eventGuestRepository.save(eventGuest);
        }
    }
}
