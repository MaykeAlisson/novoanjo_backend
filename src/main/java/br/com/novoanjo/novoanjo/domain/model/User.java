package br.com.novoanjo.novoanjo.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {

    private Long id;
    private String name;
    private Profile profile;
    private Phone phone;
    private Address address;
}
