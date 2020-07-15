package com.agan.tacocloud.po;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany(targetEntity = com.agan.tacocloud.po.Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<com.agan.tacocloud.po.Ingredient> ingredients;

    @PrePersist
    void createAt() {
        this.createdAt = new Date();
    }
}
