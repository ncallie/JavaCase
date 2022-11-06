package ru.ncallie.JavaCase.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.modelmapper.ModelMapper;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Configuration
public class OtherBeansConfig {
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
