package br.com.novoanjo.novoanjo.domain;

import br.com.novoanjo.novoanjo.commons.constant.Approved;
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
@Table(name = "event")
public class Event implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "approved", nullable = false, length = 1)
    private Approved approved;

    @Column(name = "user_id")
    private User user;

    @Column(name = "address_id")
    private Address address;
}