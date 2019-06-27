package ca.bnc.nbfg.devops.repository;

import ca.bnc.nbfg.devops.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event,Long>{
    @Query("SELECT e FROM Event e WHERE e.startDate BETWEEN :fromDate AND :toDate")
    public List<Event> getEventByPeriod(@Param("fromDate") LocalDateTime startDate, @Param("toDate") LocalDateTime endDate);

    @Query("SELECT e FROM Event e JOIN e.guests g WHERE g.email = :email AND e.startDate BETWEEN :fromDate AND :toDate")
    List<Event> getMyEventsByPeriod(@Param("email") String email, @Param("fromDate") LocalDateTime startDate, @Param("toDate") LocalDateTime endDate);

}
