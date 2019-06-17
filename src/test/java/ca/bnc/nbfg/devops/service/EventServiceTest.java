package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.repository.EventRepository;
import org.assertj.core.api.Assertions;
import ca.bnc.nbfg.devops.model.Guest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void deleteCanceledEventCanceled_Success() {
        //setup
        Event event = new Event();
        event.setId(4242L);
        event.setDescription("description");
        event.setCanceled(true);

        Mockito.when(eventRepositoryMock.findById(event.getId())).thenReturn(Optional.of(event));

        eventService.deleteEvent( event.getId() );

        Assertions.assertThat( event.isCanceled() ).isTrue();
        Mockito.verify(eventRepositoryMock).findById(4242L);
        Mockito.verify(eventRepositoryMock).delete(event);
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
    public void deleteCanceledEventNotCanceled_Success() {
        //setup
        Event event = new Event();
        event.setId(4242L);
        event.setDescription("description");
        event.setCanceled(false);

        Mockito.when(eventRepositoryMock.findById(event.getId())).thenReturn(Optional.of(event));

        eventService.deleteEvent( event.getId() );

        Assertions.assertThat( event.isCanceled() ).isFalse();
        Mockito.verify(eventRepositoryMock).findById(4242L);
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
    public void deleteCanceledEventNotFound_Success() {
        //setup
        final Long ID = Long.valueOf(4242);
        Mockito.when(eventRepositoryMock.findById(ID)).thenReturn(Optional.ofNullable(null));

        eventService.deleteEvent( ID);

        Mockito.verify(eventRepositoryMock).findById(ID);
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
    public void updateEvent_Success() {
        // setup
        final Long EVENT_ID = Long.valueOf(312);

        Event eventMock = Mockito.mock(Event.class);
        Optional<Event> optional = Optional.of(eventMock);

        Mockito.when(eventRepositoryMock.findById(EVENT_ID)).thenReturn(optional);

        // act
        boolean result = eventService.updateEvent(EVENT_ID, eventMock);

        // assert
        Mockito.verify(eventRepositoryMock).findById(EVENT_ID);
        Mockito.verify(eventRepositoryMock).save(eventMock);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void updateEvent_NotFound_ReturnFalse() {
        // setup
        final Long EVENT_ID = Long.valueOf(312);
        Event eventMock = Mockito.mock(Event.class);
        Optional<Event> optional = Optional.ofNullable(null);

        Mockito.when(eventRepositoryMock.findById(EVENT_ID)).thenReturn(optional);

        // act
        boolean result = eventService.updateEvent(EVENT_ID, eventMock);

        // assert
        Mockito.verify(eventRepositoryMock).findById(EVENT_ID);
        Mockito.verifyNoMoreInteractions(eventRepositoryMock);
        Assertions.assertThat(result).isFalse();
    }

}
