package com.eventBooker.security.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.eventBooker.dtos.request.LoginRequest;
import com.auth0.jwt.JWT;
import com.eventBooker.dtos.response.BaseResponse;
import com.eventBooker.dtos.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;


@AllArgsConstructor
public class CustomerUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response){
        try{
            //1. retrieve auth credentials from request body
            InputStream requestBodyStream = request.getInputStream();
            //2.convert json data from 1. to  java object
            LoginRequest loginRequest = mapper.readValue(requestBodyStream, LoginRequest.class);
            //3. create authentication object that is not yet authenticated
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            //4. Pass the authenticated Authentication object to d authenticationManager
            //4b. get back authentication result from authentication manager
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            //put the authentication result in the security context
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = generateAccessToken(authResult);
        LoginResponse loginResponse = LoginResponse.builder()
               .message("Successful authentication").token(token).build();
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(true);
        baseResponse.setCode(HttpStatus.OK.value());
        response.getOutputStream().write(mapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
        chain.doFilter(request,response);
    }

    private static String generateAccessToken(Authentication authResult) {
           return  JWT.create()
                    .withIssuer("mavericks_hub")
                    .withArrayClaim("roles",getClaimsFrom(authResult.getAuthorities()))
                    .withExpiresAt(Instant.now().plusSeconds(24*60*60))
                    .sign(Algorithm.HMAC512("secret"));
    }

    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority>grantedAuthorities){
        return grantedAuthorities.stream()
                .map((grantedAuthority)->grantedAuthority.getAuthority())
                .toArray(String[]::new);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(false);
        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().write(mapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
    }
}
