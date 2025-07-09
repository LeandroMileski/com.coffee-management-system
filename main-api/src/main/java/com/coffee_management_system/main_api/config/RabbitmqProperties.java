package com.coffee_management_system.main_api.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Validated
@Data
public class RabbitmqProperties {
    @NotBlank(message = "RabbitMQ host must not be blank")
    private String host;

    @Positive(message = "RabbitMQ port must be positive")
    private int port;

    @NotBlank(message = "RabbitMQ username must not be blank")
    private String username;

    private String password; // Optional for some setups
}
