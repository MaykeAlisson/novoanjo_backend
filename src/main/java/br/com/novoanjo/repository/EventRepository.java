package br.com.novoanjo.repository;

import br.com.novoanjo.domain.commons.constante.Approved;
import br.com.novoanjo.domain.model.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @EntityGraph(attributePaths = {"user", "user.phone", "address"})
    Set<Event> findByApprovedAndDataAfter(Approved status, LocalDateTime data);

    @EntityGraph(attributePaths = {"user", "user.phone", "address"})
    @Query(value = """
            SELECT e FROM Event e
            INNER JOIN e.address ad
            WHERE ad.state = :state
            AND e.approved = 'S'
            AND e.data > :dataBase
            """)
    Set<Event> findByState(String state, LocalDateTime dataBase);
}
