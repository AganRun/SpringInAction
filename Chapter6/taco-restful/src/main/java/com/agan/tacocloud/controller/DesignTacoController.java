package com.agan.tacocloud.controller;

import java.security.Principal;
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
import com.agan.tacocloud.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco design() {
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

        return "design";
    }

    @PostMapping
//    public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order, Principal principal) {
    //principal.getName()   获取用户的username,从而获取用户信息  会掺杂安全代码

//    public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order, Authentication authentication) {
//        User user = (User) authentication.getPrincipal();       //强转获取User对象

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();   //麻烦，但是任何地方都可以，不限制在Controller中

    public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "design";
        }
        Taco saved = tacoRepo.save(taco);
        order.addDesign(saved);
        order.setUser(user);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
