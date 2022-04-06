package br.com.novoanjo.novoanjo.service.user.impl;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.*;
import br.com.novoanjo.novoanjo.domain.model.Profile;
import br.com.novoanjo.novoanjo.domain.model.ServiceModel;
import br.com.novoanjo.novoanjo.domain.model.User;
import br.com.novoanjo.novoanjo.repository.ProfileRepository;
import br.com.novoanjo.novoanjo.repository.ServiceRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.infra.exception.NotFoundException;
import br.com.novoanjo.novoanjo.infra.util.jwt.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static br.com.novoanjo.novoanjo.domain.commons.dto.UserInfoDto.toUserInfoDto;
import static br.com.novoanjo.novoanjo.domain.model.User.convertToUser;
import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ServiceRepository serviceRepository;

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
    public Set<UserInfoDto> findAllByProfile(final ProfileName profileName) {

        final Profile profile = profileRepository.findByProfileName(profileName)
                .orElseThrow(() -> new NotFoundException(format("not found profile with name %s", profileName.getValor())));

        return toUserInfoDto(userRepository.findByProfile(profile));
    }

    @Override
    public Set<UserInfoDto> findAllByService(final Long idService) {

        final ServiceModel service = serviceRepository.findById(idService)
                .orElseThrow(() -> new NotFoundException(format("not found service with id %s", idService)));

        return toUserInfoDto(userRepository.findServiceAndProfileNotS(service.getId()));
    }

    @Override
    public void userToService(final UserToServiceDto userToService, final Long idUser) {

        Set<ServiceModel> services = userToService.getServices()
                .stream()
                .map(idService -> serviceRepository.findById(idService)
                        .orElseThrow(() -> new NotFoundException(format("not found service with id %s", idService))))
                .collect(Collectors.toSet());

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("not found user with id %s", idUser)));

        services.forEach(user::addService);

        userRepository.save(user);

    }

    @Override
    public void userRemoveService(final UserToServiceDto userToService, final Long idUser) {

        Set<ServiceModel> services = userToService.getServices()
                .stream()
                .map(idService -> serviceRepository.findById(idService)
                        .orElseThrow(() -> new NotFoundException(format("not found service with id %s", idService))))
                .collect(Collectors.toSet());

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("not found user with id %s", idUser)));

        services.forEach(user::removeService);

        userRepository.save(user);

    }

    @Override
    public Set<UserInfoDto> userDiscoverService(final Long idUser){

        final User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("not found user with id %s", idUser)));

        final String state = user.getAddress().getState();

    }
}
