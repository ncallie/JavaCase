package ru.ncallie.JavaCase.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.JavaCase.dto.UserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.dto.UserDto;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.services.VkService;
import ru.ncallie.JavaCase.utils.Convert;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsersController {
    private final VkService vkService;
    private final Convert convert;

    @PostMapping("/users")
    public ResponseEntity getUser(@RequestHeader("vk_service_token") String token,
                                  @Valid @RequestBody UserAndGroupIdRequestDto requestBody, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.toMessages(bindingResult));
        User user = vkService.getUser(requestBody, token);
        UserDto userDto = convert.toUserDto(user);
        return ResponseEntity.ok().body(userDto);
    }


}
