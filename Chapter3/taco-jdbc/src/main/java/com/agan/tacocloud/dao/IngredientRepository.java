package com.agan.tacocloud.dao;

import com.agan.tacocloud.pojo.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    Ingredient save(Ingredient ingredient);

}
