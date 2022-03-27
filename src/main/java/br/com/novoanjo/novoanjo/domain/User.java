package br.com.novoanjo.novoanjo.domain;

import br.com.novoanjo.novoanjo.commons.dto.UserRequestDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class User implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "zip_code", nullable = false, length = 3)
    private String name;

    @Column(name = "zip_code", nullable = false, length = 3)
    private LocalDateTime birth;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 70)
    private String password;


    private Profile profile;


    private Phone phone;


    private Address address;


    public static User convert(final UserRequestDto userRequest){

        return User.builder()
                .name("")
                .build()
    }
}
