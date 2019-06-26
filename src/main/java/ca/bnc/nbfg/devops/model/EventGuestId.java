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

}
