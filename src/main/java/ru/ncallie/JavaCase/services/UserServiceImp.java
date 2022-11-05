package ru.ncallie.JavaCase.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ncallie.JavaCase.exceptions.DataInUseException;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.repositories.UserRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    @Override
    public void save(User user) {
        validation(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private void validation(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DataInUseException("Username " + user.getUsername() + " is already in use.");
    }


}
