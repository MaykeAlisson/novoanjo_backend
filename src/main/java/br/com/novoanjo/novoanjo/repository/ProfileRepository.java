package br.com.novoanjo.novoanjo.repository;

import br.com.novoanjo.novoanjo.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByProfileName(ProfileName profileName);
}
