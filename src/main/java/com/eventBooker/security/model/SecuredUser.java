package com.eventBooker.security.model;

import com.eventBooker.data.models.Organizer;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SecuredUser implements UserDetails {
    private final Organizer user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return user.getAuthourities().stream()
                .map(authourity->new SimpleGrantedAuthority(authourity.name()))
                .collect(Collectors.toSet());
    }
    @Override
    public String getPassword(){
        return user.getPassword();
    }
    @Override
    public String getUsername(){
        return user.getEmail();
    }
    @Override
    public boolean isAccountNonExpired(){
        return UserDetails.super.isAccountNonExpired();
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return true;
    }
}
