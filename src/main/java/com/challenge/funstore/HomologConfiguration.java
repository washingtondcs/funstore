package com.challenge.funstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("homolog")
public class HomologConfiguration {

    @Bean
    public CommandLineRunner start(){
        return args -> {
            System.out.println("Homolog environment started");
        };
    }
}
