package com.spring.onboardingtask.global.security;

import com.spring.onboardingtask.user.entity.Authority;
import com.spring.onboardingtask.user.entity.User;
import com.spring.onboardingtask.user.eums.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> userAuth = user.getAuthorities();
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (Authority authority : userAuth) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
        }

        return authorities;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
