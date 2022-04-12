package br.com.novoanjo.novoanjo.domain.model;

import br.com.novoanjo.novoanjo.domain.commons.constante.Approved;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static br.com.novoanjo.novoanjo.domain.model.Address.convertToAddress;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "event")
public class Event implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "approved", nullable = false, length = 1, columnDefinition = "text")
    private Approved approved;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    public static Event toEvent(final EventRequestDto dto, final User user){

        return Event.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .data(dto.getData())
                .approved(Approved.N)
                .user(user)
                .address(convertToAddress(dto.getAddress()))
                .build();
    }
}