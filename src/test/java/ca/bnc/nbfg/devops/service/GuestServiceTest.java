package ca.bnc.nbfg.devops.service;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.EventGuest;
import ca.bnc.nbfg.devops.model.EventGuestId;
import ca.bnc.nbfg.devops.model.Guest;
import ca.bnc.nbfg.devops.repository.EventGuestRepository;
import ca.bnc.nbfg.devops.repository.GuestRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private GuestRepository guestRepositoryMock;

    @Mock
    private EventGuestRepository eventGuestRepositoryMock;

    @Test
    public void setAcceptedFlagTest_Success(){
        //setup
        EventGuestId eventGuestId = new EventGuestId(1111l,2222l);
        EventGuest eventGuest = new EventGuest();
        Event event = new Event();
        event.setId(eventGuestId.getEventId());
        Guest guest = new Guest();
        guest.setId(eventGuestId.getGuestId());
        eventGuest.setEvent(event);
        eventGuest.setGuest(guest);
        eventGuest.setInvitationStatus(EventGuest.InvitationStatus.ACCEPTED);
        Optional<EventGuest> eventGuestOptional = Optional.of(eventGuest);
        Mockito.when(eventGuestRepositoryMock.findById(eventGuestId)).thenReturn(eventGuestOptional) ;

        //test
        guestService.setInvitationStatus(eventGuestId.getGuestId(),eventGuestId.getEventId(),EventGuest.InvitationStatus.ACCEPTED);

        //assert
        Assert.assertEquals(EventGuest.InvitationStatus.ACCEPTED,eventGuest.getInvitationStatus());
        Mockito.verify(eventGuestRepositoryMock).findById(eventGuestId);
        Mockito.verify(eventGuestRepositoryMock).save(eventGuest);
        Mockito.verifyNoMoreInteractions(eventGuestRepositoryMock);
    }

    @Test
    public void setAcceptedFlagTest_SuccessNotFound(){
        //setup
        EventGuestId eventGuestId = new EventGuestId(1111l,2222l);
        Mockito.when(eventGuestRepositoryMock.findById(eventGuestId)).thenReturn(Optional.ofNullable(null)) ;

        //test
        guestService.setInvitationStatus(eventGuestId.getGuestId(),eventGuestId.getEventId(),EventGuest.InvitationStatus.ACCEPTED);

        //assert
        Mockito.verify(eventGuestRepositoryMock).findById(eventGuestId);
        Mockito.verifyNoMoreInteractions(eventGuestRepositoryMock);
    }

}
