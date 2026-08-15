package com.example.carwash.security;

import com.example.carwash.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the user's roles into Spring Security authorities (role names
        // are stored with the ROLE_ prefix already, e.g. ROLE_ADMIN)
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
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

    // isAccountNonExpired / isAccountNonLocked / isCredentialsNonExpired / isEnabled
    // are intentionally not overridden: UserDetails already provides default
    // implementations that return true, and this account model has no
    // expiry/lock/enabled flags to report otherwise.
}