package ru.ncallie.JavaCase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = PRIVATE)
@Schema(name = "User")
public class UserDto {
    @NotBlank
    @Size(min = 5, max = 15, message = "От 5 до 15 символов") @Pattern(regexp = "\\w+",
            message = "Только латинские прописные буквы")
    @Schema(description = "Username", example = "rjutta", minimum = "5", maximum = "15", pattern = "\\w+", required = true)
    String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Минимум восемь символов, минимум одна заглавная буква, одна строчная буква и одна цифра")
    @Schema(description = "User identifier", example = "123456Qq", pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", required = true)
    String password;
}
