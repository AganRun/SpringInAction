package com.agan.tacocloud.Dao;

import com.agan.tacocloud.pojo.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id, name, type from Ingredient",
                this::mapRowToIngredient);
    }

    private Ingredient mapRowToIngredient(ResultSet resultSet, int i) throws SQLException {
        return new Ingredient(
                resultSet.getString("id"),
                resultSet.getString("name"),
                Ingredient.Type.valueOf(resultSet.getString("type"))
        );
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbcTemplate.queryForObject("select id, name, type from Ingredient where id = ?",
                this::mapRowToIngredient);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(), ingredient.getName(), ingredient.getType().name());
        return ingredient;
    }
}
