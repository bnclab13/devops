package ca.bnc.nbfg.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "EVENT_ID")
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    private boolean canceled;

    /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.guest", cascade={CascadeType.MERGE,CascadeType.PERSIST})*/
    //private List<Guest> guests = new ArrayList<>();



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade={CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties("event")
    private List<GuestInvitation> guestsInvitation = new ArrayList<>();

    public List<Guest> getGuests() {
        return new ArrayList<Guest>();
    }

    public void setGuests(List<Guest> guests) {
        //this.guests = guests;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public List<GuestInvitation> getGuestsInvitation() {
        return guestsInvitation;
    }

    public void setGuestsInvitation(List<GuestInvitation> guestsInvitation) {
        this.guestsInvitation = guestsInvitation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return canceled == event.canceled &&
                Objects.equals(id, event.id) &&
                Objects.equals(startDate, event.startDate) &&
                Objects.equals(endDate, event.endDate) &&
                Objects.equals(title, event.title) &&
                Objects.equals(description, event.description) /*&&
        Objects.equals(guests, event.guests)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, title, description, canceled/*, guests*/);
    }
}
