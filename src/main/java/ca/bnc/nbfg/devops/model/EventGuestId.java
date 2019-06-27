package ca.bnc.nbfg.devops.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventGuestId implements Serializable {

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getGuestId() {
        return guestId;
    }

    public void setGuestId(long guestId) {
        this.guestId = guestId;
    }

    @Column(name = "event_id")
    private long eventId;

    @Column(name = "guest_id")
    private long guestId;


    public EventGuestId() {
    }

    public EventGuestId(long eventId, long guestId) {
        this.eventId=eventId;
        this.guestId=guestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventGuestId that = (EventGuestId) o;
        return eventId == that.eventId &&
                guestId == that.guestId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, guestId);
    }
}
