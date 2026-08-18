package com.example.carwash.security;

import com.example.carwash.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Adapts a {@link User} entity to the {@link UserDetails} contract Spring
 * Security expects. {@code isAccountNonExpired}/{@code isAccountNonLocked}/
 * {@code isCredentialsNonExpired}/{@code isEnabled} are intentionally not
 * overridden: {@link UserDetails} already defaults all four to
 * {@code true}, and this account model has no expiry/lock/enabled flags to
 * report otherwise.
 */
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role names are stored with the ROLE_ prefix already (e.g. ROLE_ADMIN),
        // matching what hasRole()/hasAnyRole() expect.
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}