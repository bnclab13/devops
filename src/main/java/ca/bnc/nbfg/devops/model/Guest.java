package ca.bnc.nbfg.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Guest {

    @Id
    @GeneratedValue
    @Column(name = "GUEST_ID")
    private long id;
    private String lastName;
    private String firstName;
    private String email;
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "guest")
    //@JsonIgnore
    //private List<Event> events;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "guest")
    @JsonIgnoreProperties("guest")
    private List<GuestInvitation> guestsInvitation = new ArrayList<>();

    public List<Event> getEvents() {
        return new ArrayList<Event>();
    }

    public void setEvents(List<Event> events) {
        /*this.events = events;*/
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public List<GuestInvitation> getGuestsInvitation() {
        return guestsInvitation;
    }

    public void setGuestsInvitation(List<GuestInvitation> guestsInvitation) {
        this.guestsInvitation = guestsInvitation;
    }


}
