package br.com.novoanjo.novoanjo.repository;

import br.com.novoanjo.novoanjo.domain.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceModel, Long> {
}
