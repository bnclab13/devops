package ca.bnc.nbfg.devops.repository;

import ca.bnc.nbfg.devops.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long>{}
