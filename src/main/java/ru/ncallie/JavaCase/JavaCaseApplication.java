package ru.ncallie.JavaCase;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Wrapper over VK API", version = "1.0"))
public class JavaCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCaseApplication.class, args);
    }
}
