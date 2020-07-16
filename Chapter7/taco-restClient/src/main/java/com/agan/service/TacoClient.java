package com.agan.service;

import com.agan.tacocloud.po.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
        //1.getForObject
//        return rest.getForObject("http://localhost:8080/ingredients/{id}",
//                Ingredient.class, ingredientId);

        //2.getForObject Map封装参数
//        Map<String, Object> params = new HashMap<>(1);
//        params.put("id", ingredientId);
//        return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, params);

        //3. getForEntity
        ResponseEntity<Ingredient> reposnse = rest.getForEntity("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
        log.info("header:" + reposnse);
        return reposnse.getBody();
    }

    /**
     * PUT请求
     * @param ingredient
     */
    public void updateIngredient(Ingredient ingredient) {
        rest.put("http://localhost:8080/ingredients/{id}",
                ingredient, ingredient.getId());
    }

    /**
     * POST 请求
     * @param ingredient
     * @return
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject("http://localhost:8080/ingredients",
                ingredient, Ingredient.class);
    }

    /**
     * DELETE 请求
     * @param ingredient
     */
    public void deleteIngredient(Ingredient ingredient) {
        rest.delete("http://localhost:8080/ingredients/{id}",
                ingredient.getId());
    }

}
