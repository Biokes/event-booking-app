package com.eventBooker.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Events")
@Setter
@Getter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private LocalDateTime startDate;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "event_organizers_id")
    private Organizer organizer;

}
