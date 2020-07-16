package com.agan.service;

import com.agan.tacocloud.po.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TacoClient {

    @Autowired
    private RestTemplate rest;

    /**
     * 根据ID获取配料
     * @param ingredientId 配料ID
     * @return 配料信息
     */
    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

}
