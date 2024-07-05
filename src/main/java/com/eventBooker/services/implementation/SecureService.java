package com.eventBooker.services.implementation;

import com.eventBooker.data.models.Organizer;
import com.eventBooker.data.repo.OrganizerRepository;
import com.eventBooker.services.interfaces.OrganizerService;
import com.eventBooker.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecureService implements UserDetailsService {
    private final OrganizerService organizerService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            Organizer user =   organizerService.findOrganizerByEmail(email);
        }

    }
}
