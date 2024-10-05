package ru.miaat.Ecommerce.Security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.miaat.Ecommerce.Entity.User;
import ru.miaat.Ecommerce.Repository.UserRepository;

@Slf4j
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
        UserDetails res = AuthUser.builder().user(user).build();
        log.info("AuthUser created by {} found with password {}", username, user.getPassword());
        return res;
    }
}
