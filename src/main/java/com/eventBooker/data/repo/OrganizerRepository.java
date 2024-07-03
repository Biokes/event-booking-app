package com.eventBooker.data.repo;

import com.eventBooker.data.models.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    @Query("select m from Organizer m where m.email = :email")
    Optional<Organizer> findByEmail(@Param("email") String email);
}
