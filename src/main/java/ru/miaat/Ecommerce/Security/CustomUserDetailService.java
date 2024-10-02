package ru.miaat.Ecommerce.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.miaat.Ecommerce.Entity.User;
import ru.miaat.Ecommerce.Repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(username)
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User +" + username + " not found")
                    );
        return AuthUser.builder().user(user).build();
    }
}
