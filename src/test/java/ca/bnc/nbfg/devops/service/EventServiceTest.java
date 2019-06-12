package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.Event;
<<<<<<< HEAD
import ca.bnc.nbfg.devops.repository.EventRepository;
import org.assertj.core.api.Assertions;
import ca.bnc.nbfg.devops.model.Guest;
=======
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.repository.EventRepository;
>>>>>>> 0c1286d2b0ca6fa8f0d3cf8cb78cc100e7e2d8c0
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepositoryMock;

    @Test
    public void createEventTest_Success(){
        //setup
        Event eventInput = new Event();
        eventInput.setDescription("description");
        Event eventOutput = new Event();
        eventOutput.setDescription("description");
        eventOutput.setId(12345L);
        Mockito.when(eventRepositoryMock.save(eventInput)).thenReturn(eventOutput);

        //test
        eventOutput = eventService.createEvent(eventInput);

        //assert
        Assert.assertEquals(new Long(12345L),eventOutput.getId());
        Mockito.verify(eventRepositoryMock).save(eventInput);
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
    public void getAllEventTest_Success(){
        //setup
        Event event1 = new Event();
        event1.setId(1111L);
        event1.setDescription("description");
        Event event2 = new Event();
        event2.setId(2222L);
        event2.setDescription("description2");
        Event event3 = new Event();
        event3.setId(3333L);
        event3.setDescription("description3");
        List<Event> events =Arrays.asList(event1,event2,event3);

        Mockito.when(eventRepositoryMock.findAll()).thenReturn(events);

        //test
        List<Event> output = eventService.getAllEvents();

        //assert
        assertThat(events).isEqualTo(output);
        Mockito.verify(eventRepositoryMock).findAll();
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
    public void getSortedEventsTest_Success(){
        //setup
        Event event1 = new Event();
        event1.setId(1111L);
        event1.setStartDate(LocalDateTime.of(2019,10,13,10,10));
        event1.setDescription("description");
        Event event2 = new Event();
        event2.setId(2222L);
        event2.setStartDate(LocalDateTime.of(2019,9,10,10,10));
        event2.setDescription("description2");
        Event event3 = new Event();
        event3.setId(3333L);
        event3.setStartDate(LocalDateTime.of(2019,9,12,10,10));
        event3.setDescription("description3");
        List<Event> events =Arrays.asList(event1,event2,event3);

        Mockito.when(eventRepositoryMock.findAll()).thenReturn(events);

        //test
        List<Event> output = eventService.getSortedEvents();

        //assert
        assertThat(output).hasSize(3);
        assertThat(output).extracting(Event::getId)
                .contains(new Long(2222L), new Long(3333L),new Long(1111L));
        Mockito.verify(eventRepositoryMock).findAll();
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
<<<<<<< HEAD
    public void cancelEvent_Success() {
        //setup
        Event event1 = new Event();
        event1.setId(4242L);
        event1.setDescription("description");
        event1.setCanceled(false);

        Mockito.when(eventRepositoryMock.findById(event1.getId())).thenReturn(Optional.of(event1));

        eventService.cancelEvent( event1.getId() );

        Assertions.assertThat( event1.isCanceled() ).isTrue();
    }
=======
>>>>>>> 0c1286d2b0ca6fa8f0d3cf8cb78cc100e7e2d8c0
    public void inviteGuestsTest_Success(){
        //setup
        Event event = new Event();
        event.setId(1111L);
        Optional<Event> eventOptional = Optional.of(event);
        List<Guest> guests = new ArrayList<>();
        Guest guest = new Guest();
        guest.setEmail("test@test.com");
        guests.add(guest);
        Mockito.when(eventRepositoryMock.findById(1111L)).thenReturn(eventOptional);

        //test
        eventService.inviteGuests(new Long(1111L),guests);

        //assert
        assertThat(event.getGuests()).hasSize(1);
        Mockito.verify(eventRepositoryMock).findById(1111L);
        Mockito.verify(eventRepositoryMock).save(event);
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
    }

}
