package com.eventBooker.data.models;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;
    private int age;
    @OneToOne
    private Ticket ticket;
}
