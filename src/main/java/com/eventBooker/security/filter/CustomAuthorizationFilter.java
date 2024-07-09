package com.eventBooker.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.eventBooker.security.utils.SecurityUtils.END_POINTS;
import static com.eventBooker.security.utils.SecurityUtils.JWT_PREFIX;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1a. retrieve request path
        //1b. if request path is a public path, call next filter in chain and terminate this filters execution
        //2. retrieve access token from request header
        //3. decode access token
        //4. extract token permission
        //5. add token permission to security context
        //6. call next filter in the filterchain
        String requestPath = request.getServletPath();
        boolean isRequestPath= END_POINTS.contains(requestPath);
        if(isRequestPath)filterChain.doFilter(request, response);
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader!= null) {
            String token = authorizationHeader.substring(JWT_PREFIX.length()).strip();
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret".getBytes())).withIssuer("Event-Booker").withClaimPresence("roles").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles").asList(SimpleGrantedAuthority.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }
}