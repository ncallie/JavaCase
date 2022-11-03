package ru.ncallie.JavaCase.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ncallie.JavaCase.dto.VkResponseDto;
import ru.ncallie.JavaCase.models.User;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class VKRepositoryImp implements VkRepository {
    private final String VER_API = "5.131";
    private final RestTemplate restTemplate;

    @Override
    public User getUserById(Integer user_id, String token) {

        String USERS_GET = "https://api.vk.com/method/users.get";
        String url = UriComponentsBuilder.fromHttpUrl(USERS_GET)
                .queryParam("user_ids", user_id)
                .queryParam("fields", "nickname")
                .queryParam("access_token", token)
                .queryParam("v", VER_API)
                .build().toString();
        User user = Objects.requireNonNull(restTemplate.getForObject(url, VkResponseDto.class)).getResponse().get(0);
        return user;
    }

    @SneakyThrows
    @Override
    public boolean isMember(Integer user_id, Integer group_id, String token) {
        String IS_MEMBER = "https://api.vk.com/method/groups.isMember";
        String url = UriComponentsBuilder.fromHttpUrl(IS_MEMBER)
                .queryParam("group_id", group_id)
                .queryParam("user_id", user_id).queryParam("access_token", token)
                .queryParam("v", VER_API)
                .build().toString();
        String response = restTemplate.getForObject(url, String.class);
        JsonNode jsonNode = new ObjectMapper().readTree(response);
        if (jsonNode.findValue("response") != null)
            return jsonNode.get("response").toString().equals("1");
        return false;
    }
}
