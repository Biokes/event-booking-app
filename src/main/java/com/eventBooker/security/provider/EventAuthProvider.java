package com.eventBooker.security.provider;

import com.eventBooker.data.models.Organizer;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.eventBooker.exception.Messages.INVALID_DETAILS;

@Component
@AllArgsConstructor
public class EventAuthProvider implements AuthenticationProvider {
    private PasswordEncoder encoder;
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if(encoder.matches(password,userDetails.getPassword())){
            Authentication authResolved  = new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
            return authResolved;
        }
        throw new BadCredentialsException(INVALID_DETAILS.getMessage());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
