package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.repository.EventRepository;
import ca.bnc.nbfg.devops.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository ;

    @Autowired
    private GuestRepository guestRepository ;

    public Event createEvent(Event event)
    {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getSortedEvents() {
        return getAllEvents().stream().sorted(Comparator.comparing(Event::getStartDate)).collect(Collectors.toList());
    }

    public void cancelEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()){
            event.get().setCanceled(true);
            eventRepository.save(event.get());
        }
    }


    public void inviteGuests(Long eventId, List<Guest> guests){
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent()){
            Event event = eventOptional.get();
            event.addGuests(guests);
            eventRepository.save(event);
        }
    }

    public void deleteEvent(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()){
            Event event = eventOptional.get();
            if  (event.isCanceled()) {
                eventRepository.delete(event);
            }
        }
    }

    public boolean updateEvent(Long id, Event event) {
        boolean isUpdated = false;

        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent() &&
                !eventOptional.get().isCanceled()) {
            event.setId(id);
            eventRepository.save(event);
            isUpdated = true;
        }

        return isUpdated;
    }

    public List<Event> getEventsByGuest(String email){
        return eventRepository.getEventsByGuest(email);
    }
}
