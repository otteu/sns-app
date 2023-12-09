package dev.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@ComponentScan(basePackages = "dev.example.kafka")
@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(ApiApplication.class, args);

    }
}