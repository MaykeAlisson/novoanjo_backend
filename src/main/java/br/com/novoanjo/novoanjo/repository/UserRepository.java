package br.com.novoanjo.novoanjo.repository;

import br.com.novoanjo.novoanjo.domain.model.Profile;
import br.com.novoanjo.novoanjo.domain.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

//    @EntityGraph(attributePaths = {"itens", "formaPagamento", "restaurante", "restaurante.cozinha", "usuario", "usuario.endereco", "status"})
    Set<User> findByProfile(Profile profile);
}
