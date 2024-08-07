package com.eventBooker.data.models;

import com.eventBooker.data.enums.TicketType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@ToString
@Table(name="Tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
    @ManyToOne
    private Event event;
    private int total;
    private BigDecimal  price;
}
