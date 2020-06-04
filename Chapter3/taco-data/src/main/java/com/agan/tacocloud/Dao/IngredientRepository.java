package com.agan.tacocloud.Dao;

import com.agan.tacocloud.pojo.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
