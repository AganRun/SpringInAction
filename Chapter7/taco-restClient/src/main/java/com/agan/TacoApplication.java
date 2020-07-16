package com.agan;

import com.agan.service.TacoClient;
import com.agan.tacocloud.po.Ingredient;
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

    @Bean
    public CommandLineRunner putAnIngredient(TacoClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- PUT -------------------------");
            Ingredient before = tacoCloudClient.getIngredientById("LETC");
            log.info("BEFORE:  " + before);
            tacoCloudClient.updateIngredient(new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
            Ingredient after = tacoCloudClient.getIngredientById("LETC");
            log.info("AFTER:  " + after);
        };
    }

    @Bean
    public CommandLineRunner addAnIngredient(TacoClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- POST -------------------------");
            Ingredient chix = new Ingredient("CHIX", "Shredded Chicken", Ingredient.Type.PROTEIN);
            Ingredient chixAfter = tacoCloudClient.createIngredient(chix);
            log.info("AFTER=1:  " + chixAfter);
        };
    }

    @Bean
    public CommandLineRunner deleteAnIngredient(TacoClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- DELETE -------------------------");
            // start by adding a few ingredients so that we can delete them later...
            Ingredient beefFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
            tacoCloudClient.createIngredient(beefFajita);
            Ingredient before = tacoCloudClient.getIngredientById("BFFJ");
            log.info("BEFORE:  " + before);
            tacoCloudClient.deleteIngredient(before);
            Ingredient after = tacoCloudClient.getIngredientById("BFFJ");
            log.info("AFTER:  " + after);
        };
    }
}
