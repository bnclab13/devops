package ca.bnc.nbfg.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class EventGuest implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private EventGuestId id;

    @ManyToOne
    @MapsId("eventId")
    @JsonIgnore
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("guestId")
    private Guest guest;

    public EventGuestId getId() {
        return id;
    }

    public void setId(EventGuestId id) {
        this.id = id;
    }

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    private InvitationStatus invitationStatus = InvitationStatus.NO_RESPONSE;

    public EventGuest() {
    }

    public EventGuest(Guest guest) {
        this.guest = guest;
    }

    public EventGuest(Guest guest, Event event) {
        this.guest = guest;
        this.event = event;
        this.id = new EventGuestId(event.getId(),guest.getId());
    }



    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventGuest that = (EventGuest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(event, that.event) &&
                Objects.equals(guest, that.guest) &&
                invitationStatus == that.invitationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, guest, invitationStatus);
    }


    public enum InvitationStatus {
        ACCEPTED,
        DECLINED,
        TENTATIVE,
        NO_RESPONSE
    }

}
