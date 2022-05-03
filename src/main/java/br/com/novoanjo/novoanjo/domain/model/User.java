package br.com.novoanjo.novoanjo.domain.model;

import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestUpdateDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static br.com.novoanjo.novoanjo.domain.model.Address.convertToAddress;
import static br.com.novoanjo.novoanjo.domain.model.Phone.convertToPhone;
import static br.com.novoanjo.novoanjo.infra.util.UtilCrypto.encriptar;
import static java.util.Objects.requireNonNullElse;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class User implements Serializable, UserDetails {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 70)
    private String password;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id")
    private Phone phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_service", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<ServiceModel> services = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    public void removeService(ServiceModel service){
        getServices().remove(service);
    }

    public void addService(ServiceModel service){
        getServices().add(service);
    }

    public static User convertToUser(final UserRequestDto userRequest, final Profile profile) {

        return User.builder()
                .name(userRequest.getName())
                .birth(userRequest.getBirth())
                .email(userRequest.getEmail())
                .password(encriptar(userRequest.getPassword()))
                .profile(profile)
                .phone(convertToPhone(userRequest.getPhone()))
                .address(convertToAddress(userRequest.getAddress()))
                .build();
    }

    public static User convertToUser(final UserRequestUpdateDto userRequest, User user) {

        user.phone.setDdd(requireNonNullElse(userRequest.getDdd(), user.phone.getDdd()));
        user.phone.setNumber(requireNonNullElse(userRequest.getNumber(), user.phone.getNumber()));
        user.address.setZipCode(requireNonNullElse(userRequest.getZipCode(), user.address.getZipCode()));
        user.address.setLogradouro(requireNonNullElse(userRequest.getLogradouro(), user.address.getLogradouro()));
        user.address.setNumber(requireNonNullElse(userRequest.getNumberAddress(), user.address.getNumber()));
        user.address.setComplement(requireNonNullElse(userRequest.getComplement(), user.address.getComplement()));
        user.address.setState(requireNonNullElse(userRequest.getState(), user.address.getState()));

        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
