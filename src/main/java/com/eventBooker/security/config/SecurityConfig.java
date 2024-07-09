package com.eventBooker.security.config;

import com.eventBooker.security.filter.CustomAuthorizationFilter;
import com.eventBooker.security.filter.CustomerUsernameAndPasswordAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Component
@AllArgsConstructor
public class SecurityConfig {
    private CustomAuthorizationFilter customAuthorizationFilter;
    private AuthenticationManager authManager;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
    var authorizationFilter = new CustomerUsernameAndPasswordAuthFilter(authManager);
    authorizationFilter.setFilterProcessesUrl("api/v1/auth");
return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                   .cors(AbstractHttpConfigurer::disable)
                   .sessionManagement(c->c.sessionCreationPolicy(STATELESS))
                   .addFilterBefore(authorizationFilter, BasicAuthenticationFilter.class)
        .authorizeHttpRequests(endpoint-> endpoint.requestMatchers("api/v1/attendee/register","").permitAll())
        .authorizeHttpRequests(endpoint->endpoint.requestMatchers("api/v1/attendee/").hasAuthority("ATTENDEE"))
        .authorizeHttpRequests(endpoint ->endpoint.requestMatchers("api/v1/organizer/").hasAuthority("ORGANIZER"))
        .build();
    }
}
