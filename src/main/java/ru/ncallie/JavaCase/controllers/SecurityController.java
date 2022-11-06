package ru.ncallie.JavaCase.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class SecurityController {
    JWTUtil jwtUtil;
    Convert convert;
    UserService userService;

    @Operation(summary = "Get JWT token", description = "Registration User and Get JWT token", tags = "Post")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get JWT",
                            content = {@Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"jwt-token\":\"Bearer eyJ0eXAiOiJKV1QiL5iaW5lbCJ9.D-4L1ItqVLbyhE_9c2YVZTXBEliwcFvLsFCjCIqQP-g\"}"
                                            )})}),
                    @ApiResponse(responseCode = "409",
                            content = {@Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"error code\":\"409\",\"error_msg\":\"Username --- is already in use.\"}"
                                            )})}),
                    @ApiResponse(responseCode = "400",
                            content = {@Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"password\":[\"Минимум восемь символов, минимум одна заглавная буква, одна строчная буква и одна цифра\"],\"username\":[\"От 5 до 15 символов\"]}"
                                            )})})
            })
    @PostMapping("/reg")
    public ResponseEntity registration(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.toMessages(bindingResult));
        User user = convert.toUser(userDto);
        userService.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok().body(Map.of("jwt-token", token));
    }


    @Operation(summary = "Refresh JWT", description = "Refresh JWT token", tags = "Post")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"jwt-token\":\"Bearer eyJ0eXAiOiJKV1QiL5iaW5lbCJ9.D-4L1ItqVLbyhE_9c2YVZTXBEliwcFvLsFCjCIqQP-g\"}"
                                            )})}),
                    @ApiResponse(responseCode = "409",
                            content = {@Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"error code\":\"409\",\"error_msg\":\"Invalid password\"}"
                                            )})}),
                    @ApiResponse(responseCode = "400",
                            content = {@Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"password\":[\"Минимум восемь символов, минимум одна заглавная буква, одна строчная буква и одна цифра\"],\"username\":[\"От 5 до 15 символов\"]}"
                                            )})})
            })
    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.toMessages(bindingResult));
        User user = convert.toUser(userDto);
        userService.validationUsernameAndPassword(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok().body(Map.of("jwt-token", token));
    }
}
