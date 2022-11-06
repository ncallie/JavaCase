package ru.ncallie.JavaCase.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonPropertyOrder({"last_name", "first_name", "nickname", "member"})
@Schema(name = "VkUserResponse")
@FieldDefaults(level = PRIVATE)
public class VkUserDto {
    @Schema(example = "Иван")
    String first_name;
    @Schema(example = "Иванов")
    String last_name;
    @Schema(example = "Иванович")
    String nickname;
    boolean isMember;

    @JsonProperty("middle_name")
    public String getNickname() {
        return nickname;
    }
}
