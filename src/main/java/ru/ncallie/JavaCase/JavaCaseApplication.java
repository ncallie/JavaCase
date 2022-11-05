package ru.ncallie.JavaCase;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Wrapper over VK API", version = "1.0"))
public class JavaCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCaseApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public CaffeineCache cacheVK() {
        return new CaffeineCache("cacheVK",
                Caffeine.newBuilder()
                        .expireAfterAccess(1, TimeUnit.HOURS)
                        .build());
    }

    @Bean
    public CaffeineCache cacheUsers() {
        return new CaffeineCache("cacheUsers",
                Caffeine.newBuilder()
                        .expireAfterWrite(1, TimeUnit.DAYS)
                        .recordStats()
                        .build());
    }
}
