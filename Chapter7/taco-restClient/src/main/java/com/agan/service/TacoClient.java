package com.agan.service;

import com.agan.tacocloud.po.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TacoClient {

    private RestTemplate rest;

    public Ingredient getIngredientById(String ingredientId) {

    }

}
