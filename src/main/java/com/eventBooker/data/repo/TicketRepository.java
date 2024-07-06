package com.eventBooker.data.repo;

import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Ticket;
import com.eventBooker.data.enums.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select m from Ticket m where m.event= :event and m.ticketType= :ticketType")
    Optional<Ticket> findByEventAndTicketType(@Param("event") Event event,@Param("ticketType")TicketType ticketType);
}
