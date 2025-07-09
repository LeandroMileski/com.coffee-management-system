package com.coffee_management_system.main_api.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtProperties {
    @NotBlank(message = "JWT secret must not be blank")
    private String secret;

    @Min(value = 3600000, message = "JWT expiration must be at least 1 hour (3600000 ms)")
    private Long expiration;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}