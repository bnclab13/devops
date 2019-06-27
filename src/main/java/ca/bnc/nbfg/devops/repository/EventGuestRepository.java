package ca.bnc.nbfg.devops.repository;

import ca.bnc.nbfg.devops.model.EventGuest;
import ca.bnc.nbfg.devops.model.EventGuestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGuestRepository extends JpaRepository<EventGuest, EventGuestId>{
}
