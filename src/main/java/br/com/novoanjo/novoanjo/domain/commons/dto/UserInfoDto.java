package br.com.novoanjo.novoanjo.domain.commons.dto;

import br.com.novoanjo.novoanjo.domain.model.Address;
import br.com.novoanjo.novoanjo.domain.model.Phone;
import br.com.novoanjo.novoanjo.domain.model.ServiceModel;
import br.com.novoanjo.novoanjo.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto implements Serializable {

    private String name;
    private Phone phone;
    private Address address;
    private Set<ServiceModel> services;

    public static Set<UserInfoDto> toUserInfoDto(Set<User> users){
        return users.stream()
                .map(user -> UserInfoDto.builder()
                        .name(user.getName())
                        .phone(user.getPhone())
                        .address(user.getAddress())
                        .build()
                ).collect(toSet());
    }

    public static Set<UserInfoDto> toUserInfoServiceCompatibleDto(Set<User> users, Set<ServiceModel> services){
        return users.stream()
                .map(user -> UserInfoDto.builder()
                        .name(user.getName())
                        .phone(user.getPhone())
                        .address(user.getAddress())
                        .services(services)
                        .build()
                ).collect(toSet());
    }

}
