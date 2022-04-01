package br.com.novoanjo.novoanjo.domain;

import br.com.novoanjo.novoanjo.commons.dto.UserRequestDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.novoanjo.novoanjo.domain.Address.convertToAddress;
import static br.com.novoanjo.novoanjo.domain.Phone.convertToPhone;
import static br.com.novoanjo.novoanjo.util.UtilCrypto.encriptar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario")
public class User implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToMany
    @JoinTable(name = "user_service", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    public static User convertToUser(final UserRequestDto userRequest, final Profile profile) {

        return User.builder()
                .name(userRequest.getName())
                .birth(userRequest.getBirth())
                .email(userRequest.getEmail())
//                .password(encriptar(userRequest.getPassword()))
                .password(userRequest.getPassword())
                .profile(profile)
                .phone(convertToPhone(userRequest.getPhone()))
                .address(convertToAddress(userRequest.getAddress()))
                .build();
    }
}
