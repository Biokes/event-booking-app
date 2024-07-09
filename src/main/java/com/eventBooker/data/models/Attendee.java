package com.eventBooker.data.models;

import com.eventBooker.data.enums.Role;
import com.eventBooker.data.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import static com.eventBooker.data.enums.Role.ATTENDEE;
import static jakarta.persistence.GenerationType.AUTO;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Visitors")
public class Attendee{
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToOne
    private Ticket ticket;
    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private Role role = ATTENDEE;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
