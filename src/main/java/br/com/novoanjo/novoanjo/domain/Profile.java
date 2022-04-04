package br.com.novoanjo.novoanjo.domain;

import br.com.novoanjo.novoanjo.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.commons.json.ProfileNameDeserializer;
import br.com.novoanjo.novoanjo.commons.json.ProfileNameSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true, columnDefinition = "text")
    private ProfileName profileName;
}
