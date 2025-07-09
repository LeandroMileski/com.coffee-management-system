package com.coffee_management_system.main_api;

import com.coffee_management_system.main_api.config.DatasourceProperties;
import com.coffee_management_system.main_api.config.JwtProperties;
import com.coffee_management_system.main_api.config.RabbitmqProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JwtProperties.class, DatasourceProperties.class, RabbitmqProperties.class})
@SpringBootApplication
public class MainApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApiApplication.class, args);
	}

}
