package ca.bnc.nbfg.devops.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    private boolean canceled;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Guest> guests = new ArrayList<>();

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                Objects.equals(description, event.description) &&
                Objects.equals(guests, event.guests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, title, description, canceled, guests);
    }
}
