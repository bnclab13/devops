package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.repository.EventRepository;
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
        if (event.get() != null){
            event.get().setCanceled(true);
            eventRepository.save(event.get());
        }

    }
}
