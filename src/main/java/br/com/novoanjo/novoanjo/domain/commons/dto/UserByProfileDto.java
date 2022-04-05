package br.com.novoanjo.novoanjo.domain.commons.dto;

import br.com.novoanjo.novoanjo.domain.model.Address;
import br.com.novoanjo.novoanjo.domain.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserByProfileDto implements Serializable {

    private String name;
    private Phone phone;
    private Address address;

}
