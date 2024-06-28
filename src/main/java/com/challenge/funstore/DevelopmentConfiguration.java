package com.challenge.funstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
public class DevelopmentConfiguration {

    @Bean
    public CommandLineRunner start(){
        return args -> {
            System.out.println("Development environment started");
        };
    }
}
