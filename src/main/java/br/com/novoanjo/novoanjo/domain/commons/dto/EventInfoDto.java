package br.com.novoanjo.novoanjo.domain.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventInfoDto implements Serializable {

    private String name;
    private String description;
    private LocalDateTime data;
    private AddressRequestDto address;
    private PhoneRequestDto phone;

}
