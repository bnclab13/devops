package ca.bnc.nbfg.devops.repository;

import ca.bnc.nbfg.devops.model.Event;
import ca.bnc.nbfg.devops.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest,Long> {

        public List<Guest> findByEmail(String email);
}
