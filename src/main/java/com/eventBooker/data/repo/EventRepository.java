package com.eventBooker.data.repo;

import com.eventBooker.data.models.Event;
import com.eventBooker.data.models.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    @Query("select e from Event e where e.organizer = :organizer ")
    List<Event> findAllByOrganizer(@Param("organizer") Organizer organizer);
}
