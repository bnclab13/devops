package ca.bnc.nbfg.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    private boolean canceled;

/*<<<<<<< HEAD
    @ManyToMany( cascade =
            { CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            },fetch = FetchType.LAZY)
    private List<Guest> guests = new ArrayList<>();
=======
>>>>>>> 7ba7dc4dad90e460d4e5b8b49897f2363af44766*/

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EventGuest> eventGuests = new HashSet<>();

    public Event() {
    }

    public Event(LocalDateTime startDate, LocalDateTime endDate, String title, String description, EventGuest... eventGuests) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;

        if (eventGuests != null) {
            for (EventGuest eventGuest : eventGuests) {
                eventGuest.setEvent(this);
            }
            this.eventGuests = Stream.of(eventGuests).collect(Collectors.toSet());
        } 
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

    public void addGuests(List<Guest> guests) {
        for (Guest g : guests) {
            EventGuest evtG = new EventGuest(g,this);
            this.eventGuests.add(evtG);
        }
    }

    public Set<EventGuest> getEventGuests() {
        return eventGuests;
    }

    public void setEventGuests(Set<EventGuest> eventGuests) {
        this.eventGuests = eventGuests;
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
                Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, title, description, canceled);
    }
}
