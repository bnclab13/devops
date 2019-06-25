package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.EventGuest;
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository ;

    public Set<EventGuest> getEventListByEmail(String email){
        List<Guest> guests = guestRepository.findByEmail(email);
        if(guests.size() == 1){
            Guest guest = guests.get(0);
            return null;
//            return guest.getEventGuestSet();
        }
        else return null;
    }
}
