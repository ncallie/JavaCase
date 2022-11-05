package ru.ncallie.JavaCase.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.JavaCase.dto.VkUserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.dto.VkUserDto;
import ru.ncallie.JavaCase.models.VkUser;
import ru.ncallie.JavaCase.services.VkService;
import ru.ncallie.JavaCase.utils.Convert;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VkUsersController {
    private final VkService vkService;
    private final Convert convert;

    @Operation(summary = "Get user", description = "Get user information and member status information", tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VkUserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Exception",
                    content = {@Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject( name = "VkApiException",
                                            value = "{\"error_code\":\"100\",\"error_msg\":\"Cannot deserialize value of type Integer from String\"}"),
                                    @ExampleObject( name = "Exception",
                                            value = "{\"group_id\":[\"не должно равняться null\"],\"user_id\":[\"не должно равняться null\"]}"
                                    )})})
    })
    @PostMapping("/vk/user")
    public ResponseEntity getUser(@RequestHeader("vk_service_token") String token,
                                  @Valid @RequestBody VkUserAndGroupIdRequestDto requestBody,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convert.toMessages(bindingResult));
        VkUser vkUser = vkService.getUser(requestBody, token);
        VkUserDto vkUserDto = convert.toVKUserDto(vkUser);
        return ResponseEntity.ok().body(vkUserDto);
    }


}
