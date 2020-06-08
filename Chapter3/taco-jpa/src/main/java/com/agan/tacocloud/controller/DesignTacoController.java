package com.agan.tacocloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.agan.tacocloud.dao.IngredientRepository;
import com.agan.tacocloud.dao.TacoRepository;
import com.agan.tacocloud.po.Ingredient;
import com.agan.tacocloud.po.Ingredient.Type;
import com.agan.tacocloud.po.Order;
import com.agan.tacocloud.po.Taco;
import com.agan.tacocloud.po.TacoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(
            @Valid TacoVo designVo, Errors errors,
            @ModelAttribute Order order) {
        //从thymeleaf提交过来的表单无法解析taco中的ingredients,尝试许久后无果
        //改变了提交策略
        if (errors.hasErrors()) {
            return "design";
        }
        List<Ingredient> ingredients = new ArrayList<>();
        for (String ingredient : designVo.getIngredients()) {
            ingredients.add(ingredientRepo.findById(ingredient));
        }
        Taco design = designVo.buildTaco(ingredients);
        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
