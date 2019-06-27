package ca.bnc.nbfg.devops.repository;

import ca.bnc.nbfg.devops.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long>{

    @Query("SELECT e FROM Event e JOIN e.guests g WHERE g.email = :email")
     List<Event> getEventsByGuest(@Param("email") String email);
}
