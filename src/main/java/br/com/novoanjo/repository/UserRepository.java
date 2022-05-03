package br.com.novoanjo.repository;

import br.com.novoanjo.domain.model.Profile;
import br.com.novoanjo.domain.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"profile"})
    Optional<User> findByEmail(String email);

    Set<User> findByProfile(Profile profile);

    @Query(value = """
            SELECT * FROM user u
            INNER JOIN user_service us
            ON us.user_id = u.id
            WHERE us.service_id = :idService
            AND u.profile_id <> (SELECT p.id FROM profile p WHERE p.name = 'S')
            """, nativeQuery = true )
    Set<User> findServiceAndProfileNotS(Long idService);

    @Query(value = """
            SELECT DISTINCT * FROM user u
            INNER JOIN user_service us
            ON us.user_id = u.id
            INNER JOIN address a
            ON a.id = u.address_id
            WHERE a.state = :state
            AND a.city = :city
            AND u.id <> :idUser
            AND u.profile_id <> (SELECT p.id FROM profile p WHERE p.name = 'S')
            AND us.service_id IN (:idsService)
            """, nativeQuery = true)
    Set<User> findServiceDescorvery(Long idUser, String city, String state, Set<Long> idsService);
}
