package ru.ncallie.JavaCase.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ncallie.JavaCase.dto.VkResponseDto;
import ru.ncallie.JavaCase.exceptions.VkApiException;
import ru.ncallie.JavaCase.models.VkUser;

import static lombok.AccessLevel.PRIVATE;

/*
Возможно опитмальный вариант это Feign REST Client
*/

@Repository
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class VKRepositoryImp implements VkRepository {
    @Value("${vk_api_version}")
    String VER_API = "5.131";
    RestTemplate restTemplate;

    @Cacheable(key = "#user_id", cacheNames = {"cacheVK"})
    @Override
    public VkUser getUserById(Integer user_id, String token) {
        log.info("Get user");
        String USERS_GET = "https://api.vk.com/method/users.get";
        String url = UriComponentsBuilder.fromHttpUrl(USERS_GET)
                .queryParam("user_ids", user_id)
                .queryParam("fields", "nickname")
                .queryParam("access_token", token)
                .queryParam("v", VER_API)
                .build().toString();
        VkUser vkUser = restTemplate.getForObject(url, VkResponseDto.class).getResponse().get(0);
        return vkUser;
    }

    @SneakyThrows
    @Cacheable(key = "#user_id.toString() + #group_id.toString()", cacheNames = {"cacheVK"})
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
        else if (jsonNode.findValue("error") != null) {
            throw new VkApiException(jsonNode.get("error").get("error_code").intValue(), jsonNode.get("error").get("error_msg").textValue());
        }
        return false;
    }
}
