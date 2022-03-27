package br.com.novoanjo.novoanjo.repository;

import br.com.novoanjo.novoanjo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
