package com.eventBooker.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.eventBooker.exception.Messages.INVALID_DETAILS;

@Component
@AllArgsConstructor
public class EventAuthManager implements AuthenticationManager {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication>authenticationType =authentication.getClass();
        if(authenticationProvider.supports(authenticationType))
            authenticationProvider.authenticate(authentication);
        throw new BadCredentialsException(INVALID_DETAILS.getMessage());
    }
}
