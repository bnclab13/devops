package ca.bnc.nbfg.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class GuestInvitation implements Serializable {


    @Id
    @GeneratedValue
private long id;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
private Event event;
    @ManyToOne
    @JoinColumn(name = "GUEST_ID")
private Guest guest;

    public GuestInvitation() {
    }

    public GuestInvitation(Event event, Guest guest) {
        this.event = event;
        this.guest = guest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    private Boolean feedbackInvitation=Boolean.TRUE;



    public void setEvent(Event event){
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest){
        this.guest = guest;
    }

    public Boolean getFeedbackInvitation() {
        return feedbackInvitation;
    }

    public void setFeedbackInvitation(Boolean feedbackInvitation) {
        this.feedbackInvitation = feedbackInvitation;
    }


}
