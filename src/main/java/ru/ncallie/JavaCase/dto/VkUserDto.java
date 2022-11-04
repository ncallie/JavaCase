package ru.ncallie.JavaCase.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonPropertyOrder({"last_name", "first_name", "nickname", "member"})
@Schema(name = "User")
public class VkUserDto {
    @Schema(example = "Иван")
    private String first_name;
    @Schema(example = "Иванов")
    private String last_name;
    @Schema(example = "Иванович")
    private String nickname;

    private boolean isMember;

    @JsonProperty("middle_name")
    public String getNickname() {
        return nickname;
    }
}
