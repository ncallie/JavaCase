package ru.ncallie.JavaCase.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public final class UserDto {
    @NotBlank
    @Size(min = 5, max = 15, message = "От 5 до 15 символов") @Pattern(regexp = "\\w+",
            message = "Только латинские прописные буквы")
    String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Минимум восемь символов, минимум одна заглавная буква, одна строчная буква и одна цифра")
    String password;
}
