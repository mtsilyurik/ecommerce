package ru.miaat.Ecommerce.Security;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.miaat.Ecommerce.Entity.User;

import java.util.Collection;
import java.util.List;

@Slf4j
@Data
@Builder
public class AuthUser implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        log.info("getPassword called by {}", user.getEmail());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        log.info("getUsername called by {}", user.getEmail());
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        log.info("isAccountNonExpired called by {}", user.getEmail());
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        log.info("isAccountNonLocked called by {}", user.getEmail());
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        log.info("isCredentialsNonExpired called by {}", user.getEmail());
        return true;
    }

    @Override
    public boolean isEnabled() {
        log.info("isEnabled called by {}", user.getEmail());
        return true;
    }
}
