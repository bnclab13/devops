package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository ;

    public List<Event> getEventListByEmail(String email){
        List<Guest> guests = guestRepository.findByEmail(email);
        if(guests.size() == 1){
            Guest guest = guests.get(0);
            return guest.getEvents();
        }
        else return null;
    }
}
