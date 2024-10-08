package com.eventBooker.security.config;

import com.eventBooker.security.filter.CustomAuthorizationFilter;
import com.eventBooker.security.filter.CustomerUsernameAndPasswordAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Component
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private CustomAuthorizationFilter customAuthorizationFilter;
    private AuthenticationManager authManager;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
    var aauthenticationFilter = new CustomerUsernameAndPasswordAuthFilter(authManager);
    aauthenticationFilter.setFilterProcessesUrl("api/v1/auth");
    return
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                   .cors(AbstractHttpConfigurer::disable)
                   .sessionManagement(c->c.sessionCreationPolicy(STATELESS))
                   .addFilterAt(aauthenticationFilter, CustomerUsernameAndPasswordAuthFilter.class)
                   .addFilterBefore(customAuthorizationFilter, CustomerUsernameAndPasswordAuthFilter.class)
        .authorizeHttpRequests(endpoint-> endpoint.requestMatchers("api/v1/organizer/register",
                "/api/v1/attendee/**").permitAll())
        .authorizeHttpRequests(endpoint ->endpoint.requestMatchers(
                "/api/v1/organizer/create-event",
                "/api/v1/organizer/addTicket",
                "/api/v1/organizer/discount-ticket",
                "/api/v1/organizer/getAllPartyAttendee",
                "/api/v1/organizer/reserve-ticket").hasAuthority("ORGANIZER"))
        .build();
    }
}