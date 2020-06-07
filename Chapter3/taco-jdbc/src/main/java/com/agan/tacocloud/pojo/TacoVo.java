package com.agan.tacocloud.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class TacoVo {

  private Long id;
  
  private Date createdAt;

  @NotNull
  @Size(min=5, message="Name must be at least 5 characters long")
  private String name;
  
  @Size(min=1, message="You must choose at least 1 ingredient")
  private List<String> ingredients;

  public Taco buildTaco(List<Ingredient> ingredients) {
      Taco result = new Taco();
      result.setName(this.name);
      result.setIngredients(ingredients);
      return result;
  }

}
