package br.com.novoanjo.novoanjo.domain;

import br.com.novoanjo.novoanjo.commons.dto.PhoneRequestDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "phone")
public class Phone implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ddd", nullable = false)
    private Short ddd;

    @Column(name = "number", nullable = false)
    private Long number;


    public static Phone convertToPhone(final PhoneRequestDto phoneRequest){
        return Phone.builder()
                .ddd(phoneRequest.getDdd())
                .number(phoneRequest.getNumber())
                .build();
    }

}
