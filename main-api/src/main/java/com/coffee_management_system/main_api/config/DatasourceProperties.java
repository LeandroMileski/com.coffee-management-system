package com.coffee_management_system.main_api.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Validated
@Data
public class DatasourceProperties {
    @NotBlank(message = "Database URL must not be blank")
    private String url;

    @NotBlank(message = "Database username must not be blank")
    private String username;

    private String password; // Password can be empty for some databases (e.g., H2)

    private HikariProperties hikari;

    @Data
    public static class HikariProperties {
        @Min(value = 1, message = "Maximum pool size must be at least 1")
        private int maximumPoolSize;

        @Min(value = 0, message = "Minimum idle connections cannot be negative")
        private int minimumIdle;
    }
}
