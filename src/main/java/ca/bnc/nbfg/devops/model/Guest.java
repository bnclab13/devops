package ca.bnc.nbfg.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Guest {

    @Id
    @GeneratedValue
    private long id;

    private String lastName;
    private String firstName;
    private String email;

    @OneToMany(
            mappedBy = "guest",
            orphanRemoval = true)
    @JsonIgnore
    private Set<EventGuest> eventGuests = new HashSet<>();

    public Guest() {
    }

    public Guest(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
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
        Guest guest = (Guest) o;
        return id == guest.id &&
                Objects.equals(lastName, guest.lastName) &&
                Objects.equals(firstName, guest.firstName) &&
                Objects.equals(email, guest.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, email);
    }
}
