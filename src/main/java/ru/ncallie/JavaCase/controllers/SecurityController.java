package ru.ncallie.JavaCase.controllers;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ncallie.JavaCase.configuration.security.JWT.JWTUtil;
import ru.ncallie.JavaCase.dto.UserDto;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.services.UserService;
import ru.ncallie.JavaCase.utils.Convert;

import javax.validation.Valid;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class SecurityController {
    JWTUtil jwtUtil;
    Convert convert;
    UserService userService;
    @PostMapping("/reg")
    public ResponseEntity registration(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.toMessages(bindingResult));
        User user = convert.toUser(userDto);
        userService.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok().body(Map.of("jwt-token", token));
    }
}
