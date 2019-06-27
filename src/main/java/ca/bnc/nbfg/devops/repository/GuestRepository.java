package ca.bnc.nbfg.devops.repository;

import ca.bnc.nbfg.devops.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest,Long> {

        public List<Guest> findByEmail(String email);
}
