package ru.ncallie.JavaCase.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ncallie.JavaCase.models.User;

public interface UserService extends UserDetailsService {
    void save(User user);

    void validationUsernameAndPassword(User user);
}
