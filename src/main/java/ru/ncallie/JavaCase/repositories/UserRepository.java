package ru.ncallie.JavaCase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncallie.JavaCase.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
