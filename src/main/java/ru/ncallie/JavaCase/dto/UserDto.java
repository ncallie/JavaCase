package ru.ncallie.JavaCase.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonPropertyOrder({"last_name", "first_name", "nickname", "member"})
public class UserDto {
    private String first_name;
    private String last_name;
    private String nickname;
    private boolean isMember;

    @JsonProperty("middle_name")
    public String getNickname() {
        return nickname;
    }
}
