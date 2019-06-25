package ca.bnc.nbfg.devops.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class EventGuest implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn
    private Guest guest;

    private boolean accepted;

    public EventGuest() {
    }

    public EventGuest(Guest guest) {
        this.guest = guest;
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
