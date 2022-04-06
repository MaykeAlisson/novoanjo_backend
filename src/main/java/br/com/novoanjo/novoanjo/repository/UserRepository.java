package br.com.novoanjo.novoanjo.repository;

import br.com.novoanjo.novoanjo.domain.model.Profile;
import br.com.novoanjo.novoanjo.domain.model.ServiceModel;
import br.com.novoanjo.novoanjo.domain.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

//    @EntityGraph(attributePaths = {"itens", "formaPagamento", "restaurante", "restaurante.cozinha", "usuario", "usuario.endereco", "status"})
    Set<User> findByProfile(Profile profile);

    @Query(value = """
            select * from user u
            inner join user_service us
            on us.user_id = u.id
            where us.service_id = :idService
            and u.profile_id <> (select p.id from profile p where p.name = 'S')
            """, nativeQuery = true )
    Set<User> findServiceAndProfileNotS(Long idService);

}
