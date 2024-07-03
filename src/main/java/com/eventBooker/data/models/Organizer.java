package com.eventBooker.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Table(name="organizers")
@Entity
@Setter
@Getter
public class Organizer {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    @Column(unique = true)
    @Email(regexp="^(?=[a-zA-Z])[a-zA-Z]+([0-9]*)([_+!`]*)+@(?=[a-zA-Z])([a-zA-Z]+)([0-9]*)([a-zA-Z0-9._!~+-]*)+\\.[a-zA-Z]{2,}$")
    private String email;
    private String password;
    private String phoneNumber;

}
