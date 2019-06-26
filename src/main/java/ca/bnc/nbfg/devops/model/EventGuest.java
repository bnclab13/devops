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

    private boolean accepted;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventGuest that = (EventGuest) o;
        return accepted == that.accepted &&
                Objects.equals(event, that.event) &&
                Objects.equals(guest, that.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, guest, accepted);
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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
