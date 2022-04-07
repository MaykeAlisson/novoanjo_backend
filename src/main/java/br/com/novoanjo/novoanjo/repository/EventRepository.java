package br.com.novoanjo.novoanjo.repository;

import br.com.novoanjo.novoanjo.domain.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
