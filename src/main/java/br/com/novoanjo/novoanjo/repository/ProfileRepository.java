package br.com.novoanjo.novoanjo.repository;

import br.com.novoanjo.novoanjo.commons.constant.ProfileName;
import br.com.novoanjo.novoanjo.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByProfileName(ProfileName profileName);
}
