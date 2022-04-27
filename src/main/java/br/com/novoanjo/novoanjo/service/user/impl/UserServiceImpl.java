package br.com.novoanjo.novoanjo.service.user.impl;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.*;
import br.com.novoanjo.novoanjo.domain.model.Profile;
import br.com.novoanjo.novoanjo.domain.model.ServiceModel;
import br.com.novoanjo.novoanjo.domain.model.User;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.infra.exception.NotFoundException;
import br.com.novoanjo.novoanjo.infra.util.jwt.Token;
import br.com.novoanjo.novoanjo.repository.ProfileRepository;
import br.com.novoanjo.novoanjo.repository.ServiceRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static br.com.novoanjo.novoanjo.domain.commons.dto.UserInfoDto.toUserInfoDto;
import static br.com.novoanjo.novoanjo.domain.commons.dto.UserInfoDto.toUserInfoServiceCompatibleDto;
import static br.com.novoanjo.novoanjo.domain.model.User.convertToUser;
import static java.lang.String.format;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccessDto createUser(final UserRequestDto userRequest) {

        log.info("UserServiceImpl.createUser - start - UserRequestDto {}", userRequest);
        final Profile profile = profileRepository.findByProfileName(userRequest.getProfileName())
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado perfil com o nome %s", userRequest.getProfileName().getValor())));

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent())
            throw new BussinesException(format("Usuario com email %s já cadastrado!", userRequest.getEmail()));

        final User user = userRepository.save(convertToUser(userRequest, profile));

        final String token = Token.gerar(user.getId(), user.getProfile().getProfileName().getValor())
                .orElseThrow(() -> new BussinesException("Erro ao Gerar token!"));

        log.info("UserServiceImpl.createUser - end ");
        return UserAccessDto.builder()
                .id(user.getId())
                .name(user.getName())
                .profile(profile.getProfileName().getValor())
                .token(token)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUser(final UserRequestUpdateDto userRequestUpdate, final Long id) {

        log.info("UserServiceImpl.updateUser - start - UserRequestUpdateDto {} - id {}", userRequestUpdate, id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado usuario com id %s", id)));

        userRepository.save(convertToUser(userRequestUpdate, user));

        log.info("UserServiceImpl.updateUser - end ");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<UserInfoDto> findAllByProfile(final ProfileName profileName) {

        log.info("UserServiceImpl.findAllByProfile - start - ProfileName {}", profileName);
        final Profile profile = profileRepository.findByProfileName(profileName)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado perfil com o nome %s", profileName.getValor())));

        log.info("UserServiceImpl.findAllByProfile - end ");
        return toUserInfoDto(userRepository.findByProfile(profile));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<UserInfoDto> findAllByService(final Long idService) {

        log.info("UserServiceImpl.findAllByService - start - idService {}", idService);
        final ServiceModel service = serviceRepository.findById(idService)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado service com id %s", idService)));

        log.info("UserServiceImpl.findAllByService - end");
        return toUserInfoDto(userRepository.findServiceAndProfileNotS(service.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void userToService(final UserToServiceDto userToService, final Long idUser) {

        log.info("UserServiceImpl.userToService - start - UserToServiceDto {} - idUser {}", userToService, idUser);
        Set<ServiceModel> services = userToService.getServices()
                .stream()
                .map(idService -> serviceRepository.findById(idService)
                        .orElseThrow(() -> new NotFoundException(format("Não foi encontrado service com id %s", idService))))
                .collect(Collectors.toSet());

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado usuario com id %s", idUser)));

        services.forEach(user::addService);

        userRepository.save(user);

        log.info("UserServiceImpl.userToService - end ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void userRemoveService(final UserToServiceDto userToService, final Long idUser) {

        log.info("UserServiceImpl.userRemoveService - start - UserToServiceDto {} - idUser {}", userToService, idUser);
        Set<ServiceModel> services = userToService.getServices()
                .stream()
                .map(idService -> serviceRepository.findById(idService)
                        .orElseThrow(() -> new NotFoundException(format("Não foi encontrado service com id %s", idService))))
                .collect(Collectors.toSet());

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado usuario com id %s", idUser)));

        services.forEach(user::removeService);

        userRepository.save(user);
        log.info("UserServiceImpl.userRemoveService - end ");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<UserInfoDto> userDiscoverService(final Long idUser) {

        log.info("UserServiceImpl.userDiscoverService - start - idUser {}", idUser);
        final User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado usuario com id %s", idUser)));

        final String city = user.getAddress().getCity();
        final String state = user.getAddress().getState();
        final Set<Long> idsService = user.getServices().stream().map(ServiceModel::getId).collect(Collectors.toSet());

        log.info("UserServiceImpl.userDiscoverService - end ");
        return toUserInfoServiceCompatibleDto(
                userRepository.findServiceDescorvery(user.getId(), city, state, idsService),
                user.getServices()
        );
    }
}
