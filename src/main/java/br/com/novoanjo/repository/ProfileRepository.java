package br.com.novoanjo.repository;

import br.com.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByProfileName(ProfileName profileName);
}
