package com.agan;

import com.agan.service.TacoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class TacoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public CommandLineRunner fetchIngredient(TacoClient client) {
        return args -> {
            log.info("-------------GET-------------------");
            log.info("----------ingredient" + client.getIngredientById("FLTO"));
        };
    }
}
