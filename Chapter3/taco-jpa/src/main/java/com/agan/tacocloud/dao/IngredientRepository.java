package com.agan.tacocloud.dao;


import com.agan.tacocloud.po.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
