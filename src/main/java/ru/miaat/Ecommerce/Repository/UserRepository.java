package ru.miaat.Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.miaat.Ecommerce.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
