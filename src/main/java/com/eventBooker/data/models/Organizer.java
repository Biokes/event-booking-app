package com.eventBooker.data.models;

import com.eventBooker.data.enums.Authority;
import com.eventBooker.data.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static com.eventBooker.data.enums.Role.ORGANIZER;
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
    @Enumerated(EnumType.STRING)
    private Authority authourities;
    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private Role role = ORGANIZER;
}
