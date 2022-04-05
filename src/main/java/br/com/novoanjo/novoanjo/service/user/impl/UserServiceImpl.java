package br.com.novoanjo.novoanjo.service.user.impl;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserByProfileDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestUpdateDto;
import br.com.novoanjo.novoanjo.domain.model.Profile;
import br.com.novoanjo.novoanjo.domain.model.User;
import br.com.novoanjo.novoanjo.repository.ProfileRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.infra.exception.NotFoundException;
import br.com.novoanjo.novoanjo.infra.util.jwt.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static br.com.novoanjo.novoanjo.domain.model.User.convertToUser;
import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserAccessDto createUser(final UserRequestDto userRequest) {

        final Profile profile = profileRepository.findByProfileName(userRequest.getProfileName())
                .orElseThrow(() -> new NotFoundException(format("not found profile with name %s", userRequest.getProfileName().getValor())));

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent())
            throw new BussinesException(format("user with already registered email %s ", userRequest.getEmail()));

        final User user = userRepository.save(convertToUser(userRequest, profile));

        final String token = Token.gerar(user.getId(), user.getProfile().getProfileName().getValor())
                .orElseThrow(() -> new BussinesException("Erro ao Gerar token!"));
        return UserAccessDto.builder()
                .id(user.getId())
                .name(user.getName())
                .profile(profile.getProfileName().getValor())
                .token(token)
                .build();
    }

    @Override
    public void updateUser(final UserRequestUpdateDto userRequestUpdate, final Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("not found user with id %s", id)));

        userRepository.save(convertToUser(userRequestUpdate, user));

    }

    @Override
    public Set<UserByProfileDto> findAllByProfile(final ProfileName profileName) {

        final Profile profile = profileRepository.findByProfileName(profileName)
                .orElseThrow(() -> new NotFoundException(format("not found profile with name %s", profileName.getValor())));

        Set<User> users = userRepository.findByProfile(profile);

        System.out.println(users);

        return null;
    }
}
